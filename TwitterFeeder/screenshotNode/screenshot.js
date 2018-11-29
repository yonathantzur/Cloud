const nightmare = require('nightmare')();

nightmare.goto(process.argv[2]).
viewport(1920, 1080).
screenshot(process.argv[3] || 'screenshot.png').
end(() => {});