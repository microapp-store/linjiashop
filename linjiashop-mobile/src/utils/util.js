export default {
    formatMobile: function (mobile) {
        return mobile.substr(0, 3) + '****' + mobile.substr(7, 11);
    }
}
