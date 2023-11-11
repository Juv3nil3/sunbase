document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const uuid = urlParams.get('uuid');

    const updateCustomerForm = document.getElementById('updateCustomerForm');

    updateCustomerForm.addEventListener('submit', function(e) {
        e.preventDefault();
        updateCustomer();
    });
        function updateCustomer() {
        const accessToken = localStorage.getItem('accessToken');
        const apiUrl = `/customer/update/${uuid}`;

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
                if (statusCode === 200) {
                    console.log('Customer updated successfully');
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
            console.error('Update customer error:', error);
            displayErrorMessage('Failed to update customer. Please try again.');
        });
    }

    function displayErrorMessage(message) {
        const errorMessageContainer = document.getElementById('errorMessage');
        errorMessageContainer.innerText = message;
        errorMessageContainer.style.display = 'block';
    }

    function getValueById(id) {
        return document.getElementById(id).value;
    }

});
