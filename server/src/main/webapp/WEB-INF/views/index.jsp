<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Test CORS</title>
    </head>
    <body>
        <h1>Test CORS</h1>
        <input id="get-printers" type="button" value="GET Printers" />
        <input id="post-printers" type="button" value="POST Printers" />
        <input id="post-json-printers" type="button" value="POST JSON Printers" />

        <h2>Result:</h2>
        <div id="result"></div>

        <!--[if lt IE 9]>
        <script src="resources/vendor/jquery-legacy/dist/jquery.min.js"></script>
        <script src="resources/vendor/jquery.base64/jquery.base64.js"></script>
        <script src="resources/vendor/jquery-ajaxtransport-xdomainrequest/jQuery.XDomainRequest.js"></script>
        <input id="ltIE9" type="hidden" />
        <![endif]-->
        <!--[if gte IE 9]><!-->
        <script src="resources/vendor/jquery-modern/dist/jquery.min.js"></script>
        <!--<![endif]-->

        <script src="resources/js/app.js"></script>
    </body>
</html>
