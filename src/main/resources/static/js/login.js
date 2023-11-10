document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const loginId = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;
    console.log(loginId);
    console.log(password);
    fetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            login_id: loginId,
            password: password,
        }),
    })
    .then(response => {
            if (!response.ok) {
                throw new Error('Authentication failed');
            }
            return response.text();  // Use text() instead of json()
        })
    .then(accessToken => {
    // Store the token in localStorage
    localStorage.setItem('accessToken', accessToken);

    // Redirect to the customer list page
    window.location.href = 'customerList.html';
    })
    .catch(error => {
    console.error('Login error:', error);
    alert('Login failed. Please check your credentials.');
    console.error(error.stack);
    console.error(error.message);
    });
});
