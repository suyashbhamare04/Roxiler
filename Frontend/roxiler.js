
const defaultPage = 1;
const defaultPerPage = 10;


async function fetchTransactions(month, searchQuery, page, perPage) {
    try {
        const response = await fetch(`/api/transactions?month=${month}&search=${searchQuery}&page=${page}&per_page=${perPage}`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching transactions:', error);
    }
}


async function fetchTransactionStatistics(month) {
    try {
        const response = await fetch(`/api/statistics?month=${month}`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching transaction statistics:', error);
    }
}


async function fetchDataForBarChart(month) {
    try {
        const response = await fetch(`/api/bar-chart?month=${month}`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching data for bar chart:', error);
    }
}


async function updateTransactionsTable(month, searchQuery, page, perPage) {
    const transactionsBody = document.getElementById('transactions-body');

   
    transactionsBody.innerHTML = '';

    const transactions = await fetchTransactions(month, searchQuery, page, perPage);

    transactions.forEach(transaction => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${transaction.id}</td>
            <td>${transaction.title}</td>
            <td>${transaction.description}</td>
            <td>${transaction.price}</td>
            <td>${transaction.category}</td>
            <td>${transaction.sold ? 'Yes' : 'No'}</td>
            <td><img src="${transaction.image}" alt="Product Image" style="width: 50px; height: auto;"></td>
        `;
        transactionsBody.appendChild(row);
    });
}

function handlePaginationNavigation(direction) {
    let currentPage = parseInt(localStorage.getItem('currentPage')) || defaultPage;
    currentPage = direction === 'next' ? currentPage + 1 : currentPage - 1;
    if (currentPage < 1) currentPage = 1; 
    localStorage.setItem('currentPage', currentPage);
    const selectedMonth = document.getElementById('month-dropdown').value;
    const searchQuery = document.getElementById('search-input').value;
    updateTransactionsTable(selectedMonth, searchQuery, currentPage, defaultPerPage);
}

async function updateTransactionStatistics(month) {
    const statisticsContainer = document.getElementById('statistics-container');

   
    statisticsContainer.innerHTML = '';

   
    const statistics = await fetchTransactionStatistics(month);

    
    for (const [statisticName, statisticValue] of Object.entries(statistics)) {
        const statisticElement = document.createElement('div');
        statisticElement.classList.add('statistic-item');
        statisticElement.innerHTML = `
            <label>${statisticName}:</label>
            <span>${statisticValue}</span>
        `;
        statisticsContainer.appendChild(statisticElement);
    }
}


async function updateBarChart(month) {
    const data = await fetchDataForBarChart(month);

    const labels = data.map(entry => entry.priceRange);
    const values = data.map(entry => entry.numItems);

    const ctx = document.getElementById('myBarChart').getContext('2d');
    
    if (window.myBarChart) {
        window.myBarChart.destroy(); 
    }

    window.myBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Number of Items',
                data: values,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}

document.getElementById('month-dropdown').addEventListener('change', function() {
    const selectedMonth = this.value;
    const searchQuery = document.getElementById('search-input').value;
    localStorage.removeItem('currentPage'); 
    updateTransactionsTable(selectedMonth, searchQuery, defaultPage, defaultPerPage);
});

document.getElementById('search-input').addEventListener('input', function() {
    const selectedMonth = document.getElementById('month-dropdown').value;
    const searchQuery = this.value;
    localStorage.removeItem('currentPage'); 
    updateTransactionsTable(selectedMonth, searchQuery, defaultPage, defaultPerPage);
});

document.getElementById('prev-page').addEventListener('click', () => handlePaginationNavigation('prev'));
document.getElementById('next-page').addEventListener('click', () => handlePaginationNavigation('next'));

document.getElementById('month-dropdown').addEventListener('change', function() {
    const selectedMonth = this.value;
    updateTransactionStatistics(selectedMonth);
});

document.getElementById('month-dropdown').addEventListener('change', function() {
    const selectedMonth = this.value;
    updateBarChart(selectedMonth);
});

const defaultSelectedMonth = 'March';
updateTransactionsTable(defaultSelectedMonth, '', defaultPage, defaultPerPage);

updateTransactionStatistics(defaultSelectedMonth);

updateBarChart(defaultSelectedMonth);
