<#include "header.ftlh">

    <div class="container">
        <div class="jumbotron jumbotron-opacity">
            <h1 class="display">Konto zostało utworzone. 🤗</h1>
            <p class="lead">Dziekujemy.</p>
            <hr class="my-4">
            <form>
                Email:<br>
                <input class="form-control" type="email" name="email" value="${user.userEmail}" disabled>
                <br>
                Imię:<br>
                <input class="form-control" type="text" name="name" value="${user.userName}" disabled>
                <br>
                Nazwisko:<br>
                <input class="form-control" type="text" name="surname" value="${user.userSurname}" disabled>
            </form>
        </div>
    </div>



</div>
<footer>&copy; 2018 JBusters</footer>
<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>