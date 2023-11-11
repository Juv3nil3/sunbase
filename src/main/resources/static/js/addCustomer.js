document.getElementById('addCustomerForm').addEventListener('submit', function (e) {
    e.preventDefault();
});

function displayErrorMessage(message) {
    const errorMessageContainer = document.getElementById('errorMessage');
    errorMessageContainer.innerText = message;
    errorMessageContainer.style.display = 'block';
}

function getValueById(id) {
    return document.getElementById(id).value;
}

function addCustomer() {
    const accessToken = localStorage.getItem('accessToken');
    const apiUrl = `/customer/create`;

    if (!accessToken) {
        window.location.href = 'login.html';
        return;
    }

    const customer = {
        first_name: getValueById('firstName'),
        last_name: getValueById('lastName'),
        street: getValueById('street'),
        address: getValueById('address'),
        city: getValueById('city'),
        state: getValueById('state'),
        email: getValueById('email'),
        phone: getValueById('phone')
    };

    console.log(customer);

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${accessToken}`,
        },
        body: JSON.stringify(customer)
    })
    .then(response => {
        if (response.ok) {
            const statusCode = response.status;
            if (statusCode === 201) {
                console.log('Customer added successfully');
                window.location.href = 'customerList.html';
            } else {
                displayErrorMessage('Unexpected success. Please try again.');
            }
        } else {
            const statusCode = response.status;
            if (statusCode === 400) {
                displayErrorMessage('First Name or Last Name is missing.');
            } else if (statusCode === 500) {
                displayErrorMessage('Invalid Command.');
            } else if (statusCode === 401) {
                displayErrorMessage('Invalid Authorization.');
                window.location.href = 'login.html';
            } else {
                console.error(`Unexpected error status code: ${statusCode}`);
                displayErrorMessage('Unexpected error. Please try again.');
            }
        }
    })
    .catch(error => {
        console.error('Add customer error:', error);
        displayErrorMessage('Failed to add customer. Please try again.');
    });
}
