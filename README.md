# U5-W2-D3 (Continuo della repository U5-W2-D2)
Esercizi sull'uso di Spring Web e Postman. 
Creazione di un set di WebApi di un'applicazione di blogging

Aggiunta la dipendenza Spring Data JPA per le operazioni con il DB Postgres.
Creata la relazione tra le due entità Blog e Autore (OneToMany) .
Utilizzato anche una gestione degli errori più dettagliata con il meccanismo del ExcepetionHandler.
Introdotto il meccanismo del Server Side Pagination, quindi con gestione della paginazione e dell'ordinamento degli elementi.

Introdotto il meccanismo della Validazione su dei records (che comprendono il payload), e l'upload delle immagini usando la dipendenza di Cloudinary

struttura del blog:
- id (generato automaticamente)
- categoria
- titolo
- cover (es:"https://picsu,.photos/200/300", generata automaticamente dal server)
- contenuto
- tempoDiLettura (in minuti)

Struttura dell'autore:
- id (generato automaticamente)
- nome
- cognome
- email
- dataDiNascita
- avatar /es: "https://ui-avatars.com/api/?name=Mario+Rossi", generato automaticamente dal server)

Endpoint presenti:
- GET /blogPosts -> ritorna la lista di blog post
- GET /blogPosts/123 -> ritorna un singolo blog post
- POST /blogPosts -> crea un nuovo blog post
- PUT /blogPosts/123 -> modifica lo specifico blog post
- DELETE /blogPosts/123 -> cancella lo specifico blog post

- GET /authors -> ritorna la lista degli autori
- GET /authors/123 -> ritorna un singolo autore
- POST /authors -> crea un nuovo autore
- PUT /authors/123 -> modifica lo specifico autore
- DELETE /authors/123 -> cancella lo specifico autore
