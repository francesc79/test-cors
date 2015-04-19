$(document).ready(function () {

    var user = 'cors';
    var password = 'cors';

    function make_base_auth(user, password) {
        var tok = user + ':' + password;
        var hash = '';
        if (window.btoa) {
            hash = window.btoa(tok);
        } else { //for <= IE9
            hash = $.base64.encode(tok);
        }
        return 'Basic ' + hash;
    }

    function crossDomainCall (url, type, user, password, contentType, data, callback) {

        var auth = make_base_auth(user, password);
        if ($('#ltIE9').length > 0) {
            url += (url.indexOf('?') > -1) ? '&' : '?';
            url += 'Authorization=' + encodeURIComponent(auth);
            if (type === 'POST') {
                url += (url.indexOf('?') > -1) ? '&' : '?';
                url += 'Content-Type=' + encodeURIComponent(contentType);
            }
            else if (type !== 'GET') {
                throw Error('type ' + type + ' not supported');
            }
        }

        $.ajax({
            url: url,
            type: type,
            data: data,
            dataType: 'json',
            contentType: contentType + '; charset=UTF-8',
            async: true,
            cache: false,
            crossDomain: true,
            beforeSend: function (xhr, opts) {
                xhr.setRequestHeader('Authorization', auth);
                return true;
            },
            success: function (resp) {
                if (callback) {
                    callback(resp);
                }
            },
            error: function(xhr) {
                alert('Error!  Status = ' + xhr.status + " Message = " + xhr.statusText);
            }
        });

    }

    $('#get-printers').on('click', function (e) {
        e.preventDefault();
        $('#result').text('');
        crossDomainCall (location.protocol + '//127.0.0.1:' + (location.protocol==='https:'?8090:8080) + '/rest/printers', 'GET', user, password,
            null, null, function (resp) {
            $('#result').text(resp);
        });
    });

    $('#post-printers').on('click', function (e) {
        e.preventDefault();
        $('#result').text('');
        crossDomainCall (location.protocol + '//127.0.0.1:' + (location.protocol==='https:'?8090:8080) + '/rest/printers', 'POST', user, password,
            'application/x-www-form-urlencoded', 'param=12', function (resp) {
            $('#result').text(resp);
        });
    });

    $('#post-json-printers').on('click', function (e) {
        e.preventDefault();
        $('#result').text('');
        crossDomainCall (location.protocol + '//127.0.0.1:' + (location.protocol==='https:'?8090:8080) + '/rest/printers', 'POST', user, password,
            'application/json', {param:123}, function (resp) {
                $('#result').text(resp);
            });
    });
});