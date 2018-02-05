'use strict';
var http = require('http');
var port = process.env.PORT || 1337;
var fs = require('fs');
var __dirname = "./frontendWeb/";
var __path = "OfflineSched.html";

http.createServer(function (req, res) {
	fs.readFile(__dirname + __path, function (err, data) {
		if (err) {
			res.writeHead(404, { 'Content-type': 'text/plan' });
			resp.write('Page Was Not Found');
			res.end();
		} else {
			res.writeHead(200, { "Content-Type": "text/html" });
			res.write(data);
			res.end();
		}
	});
}).listen(port);
