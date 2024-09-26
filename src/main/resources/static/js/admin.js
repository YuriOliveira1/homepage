
// Função para buscar posts do backend
function fetchPosts() {
    fetch('/posts')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            populateTable(data);
        })
        .catch(error => {
            console.error('Erro ao buscar posts:', error);
        });
}

function populateTable(data) {
    const tableBody = document.querySelector('#data-table tbody');
    tableBody.innerHTML = '';

    data.forEach(item => {
        const newRow = document.createElement('tr');
        newRow.setAttribute('data-id', item.id);

        newRow.innerHTML = `
            <td>${item.id}</td>
            <td>${item.slug}</td>
            <td>${item.createdAt}</td>
            <td><button class="delete-btn" data-id="${item.id}">Delete</button></td>
        `;
        tableBody.appendChild(newRow);
    });

    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', (event) => {
            const id = event.target.getAttribute('data-id');
            deleteItem(id);
        });
    });
}

// Função para excluir um post
function deleteItem(id) {
    fetch(`/posts/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                console.log('Post excluído');
                // Remova a linha da tabela
                document.querySelector(`tr[data-id="${id}"]`).remove();
            } else {
                console.error('Erro ao excluir o post');
            }
        })
        .catch(error => {
            console.error('Erro ao excluir o post:', error);
        });
}

// Chama a função para buscar os posts ao carregar a página
fetchPosts();
