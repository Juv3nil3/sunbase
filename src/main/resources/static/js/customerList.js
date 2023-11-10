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
            <td>${customer.street}</td>
            <td>${customer.address}</td>
            <td>${customer.city}</td>
            <td>${customer.state}</td>
            <td>${customer.email}</td>
            <td>${customer.phone}</td>
            <td>
                <button onclick="deleteCustomer('${customer.uuid}')">Delete</button>
                <button onclick="updateCustomer('${customer.uuid}')">Update</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

function deleteCustomer(uuid) {
    // Implement the logic to delete the customer with the specified UUID
    console.log(`Delete customer with UUID: ${uuid}`);
}

function updateCustomer(uuid) {
    // Implement the logic to update the customer with the specified UUID
    console.log(`Update customer with UUID: ${uuid}`);
}
