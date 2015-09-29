var exec = require('cordova/exec');

module.exports = {
    bind: function (message, onSuccess, onError) {
        exec(onSuccess, onError, "BDPush", "bind", [message]);
    },
    unbind: function (message, onSuccess, onError) {
        exec(onSuccess, onError, "BDPush", "unbind", [message]);
    }
};