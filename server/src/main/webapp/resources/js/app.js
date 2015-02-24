$(document).ready(function () {

    var user = 'cors';
    var password = 'cors';

    function make_base_auth(user, password) {
        var tok = user + ':' + password;
        var hash = btoa(tok);
        return 'Basic ' + hash;
    }

    $('#printers').on('click', function (e) {
        e.preventDefault();

        $.ajax({
            url: 'http://localhost:8080/rest/printers',
            type: 'GET',
            dataType: 'json',
            async: true,
            cache: false,
            crossDomain: true,
            beforeSend: function (xhr, opts) {
                var auth = make_base_auth(user, password);
                console.log('auth:' + auth);
                xhr.setRequestHeader('Authorization', auth);
            },
            success: function (resp) {
                $('#result').text(resp);
            },
            error: function(xhr) {
                alert('Error!  Status = ' + xhr.status + " Message = " + xhr.statusText);
            }
        });
    });

});