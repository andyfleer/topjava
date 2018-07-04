<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container">
    <div class="row">
        <div class="col">
            <h1 class="h3">Add meal</h1>
            <hr/>

            <form action="meals" method="post">
                <input type="hidden" name="command" value="ADD" />
                <div class="form-row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>Date</label>
                            <input type="date" class="form-control" name="date" value=""  placeholder="Enter date">
                            <small id="dateFieldSmall" class="form-text text-muted">Enter date of the meal.</small>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>Time</label>
                            <input type="time" class="form-control" name="time"  placeholder="Enter time">
                            <small id="timeFieldSmall" class="form-text text-muted">Enter time of the meal.</small>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>Calories</label>
                            <input type="text" class="form-control" name="calories" placeholder="Calories">
                            <small id="caloriesFieldSmall" class="form-text text-muted">Enter calories of the meal.</small>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col">
                        <div class="form-group">
                            <label>Description</label>
                            <input type="text" class="form-control" name="description" placeholder="Description">
                            <small>Description and notes</small>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </div>
            </form>
            <a href="index.html">Home</a>
        </div>
    </div>
</div>


</body>
</html>
