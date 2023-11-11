document.addEventListener('DOMContentLoaded', function () {
    // Retrieve the stored access token
    const accessToken = localStorage.getItem('accessToken');

    // Check if the user is authenticated
    if (!accessToken) {
        // Redirect to the login page if not authenticated
        window.location.href = 'login.html';
    } else {
        // Fetch the customer list using the stored access token
        fetchCustomerList(accessToken);
    }
});

function fetchCustomerList(accessToken) {
    fetch('/customer/getList', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to fetch customer list');
        }
        return response.json();
    })
    .then(data => {
        // Render the customer list
        renderCustomerList(data);
    })
    .catch(error => {
        console.error('Fetch customer list error:', error);
        alert('Failed to fetch customer list. Please try again.');
    });
}

function renderCustomerList(customerList) {
    const tableBody = document.getElementById('customerTableBody');

    // Clear existing rows
    tableBody.innerHTML = '';

    // Iterate through the customer list and create table rows
    customerList.forEach(customer => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${customer.first_name}</td>
            <td>${customer.last_name}</td>
            <td>${customer.address}</td>
            <td>${customer.city}</td>
            <td>${customer.state}</td>
            <td>${customer.email}</td>
            <td>${customer.phone}</td>
            <td class="button-group">
                <button class="delete" onclick="deleteCustomer('${customer.uuid}')">Delete</button>
                <button onclick="redirectToUpdateCustomer('${customer.uuid}')">Update</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

function deleteCustomer(uuid) {
    const accessToken = localStorage.getItem('accessToken');
    const apiUrl = `/customer/delete/${uuid}`;

    fetch(apiUrl, {
        method: 'DELETE', // Use DELETE method for deleting resources
        headers: {
            'Authorization': `Bearer ${accessToken}`,
        },
    })
    .then(response => {
            if (response.ok) {
                console.log('Customer deleted successfully');

                // Fetch the updated customer list after deletion
                fetchCustomerList(accessToken);
            } else if (response.status === 401) {

                console.error('Invalid authorization. Please log in again.');
                // Redirect to the login page
                window.location.href = 'login.html';
            } else if (response.status === 400) {

                displayErrorMessage('Customer not found with UUID');
            } else if (response.status === 500) {
                displayErrorMessage('Unable to delete customer with UUID');
            } else {
                // Handle other status codes if needed
                console.error(`Unexpected response: ${response.status}`);
            }
        })
        .catch(error => {
            // Handle fetch errors
            console.error('Delete customer error:', error);
            // Display a generic error message
            displayErrorMessage('Error: Something went wrong. Please try again.');
        });
}

function redirectToUpdateCustomer(uuid) {
    // Implement the logic to update the customer with the specified UUID
    window.location.href = `updateCustomer.html?uuid=${uuid}`;
}

function redirectToCreateCustomer() {
    // Assuming 'addCustomer.html' is the page where you want to add a new customer
    window.location.href = 'addCustomer.html';
}
function displayErrorMessage(message) {
        // Display the error message in the HTML
        errorMessageContainer.innerText = message;
        errorMessageContainer.style.display = 'block';
    }