const quill = new Quill('#editor', {
  modules: {
    syntax: true,
    toolbar: '#toolbar-container',
  },
  placeholder: 'Compose an epic...',
  theme: 'snow',
});

document.getElementById('post-button').addEventListener('click', async () => {
  const title = document.getElementById('post-title').value; // Obter o título do post
  const content = quill.root.innerHTML; // Obter o conteúdo do editor Quill

  // Verifica se o título ou o conteúdo estão vazios
  if (!title || !content) {
      alert('O título e o conteúdo são obrigatórios!');
      return;
  }

  // Criar um FormData para enviar o título, conteúdo e imagens
  const formData = new FormData();
  formData.append('title', title);
  formData.append('content', content);


  // Fazer a chamada para a API que irá salvar o post
  try {
      const response = await fetch('/posts', { // O endpoint '/posts' continua o mesmo
          method: 'POST',
          body: formData // Enviar FormData com o post
      });

      if (response.ok) {
          // Sucesso no envio do post
          alert('Post criado com sucesso!');
          // Limpar o campo de título e o editor
          document.getElementById('post-title').value = '';
          quill.setContents([]); // Limpar o editor Quill
      } else {
          alert('Erro ao criar o post. Tente novamente.');
      }
  } catch (error) {
      console.error('Erro:', error);
      alert('Erro ao criar o post. Tente novamente.');
  }
});