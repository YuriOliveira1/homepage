// Função para buscar posts do backend
function fetchPosts() {
    fetch('/posts')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar posts');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            displayPosts(data);
        })
        .catch(error => {
            console.error('Erro ao buscar posts:', error);
            // Exibir uma mensagem de erro para o usuário, se necessário
        });
}

// Função para exibir os posts na página
function displayPosts(posts) {
    const postsContainer = document.querySelector('.posts'); // Certifique-se de que o seletor corresponda ao seu HTML

    postsContainer.innerHTML = ''; // Limpa o container antes de adicionar novos posts

    posts.forEach(post => {
        const postCard = document.createElement('li');
        postCard.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">
                        <a id="title-post" href="/posts/${post.id}">${post.title}</a>
                    </h5>
                    <a href="#" class="card-link">${new Date(post.createdAt).toLocaleString()}</a> 
                </div>
            </div>
        `;
        postsContainer.appendChild(postCard);
    });
}

// Chama a função para buscar os posts ao carregar a página
document.addEventListener('DOMContentLoaded', fetchPosts);
