class RestUtil {
    constructor(method, url, params) {
        this.method = method;
        this.url = url;
        this.params = params;
    }

    doAjax(resultTagId) {
        $.ajax({
            method: this.method,
            url: this.url,
            data: this.params,
            contentType: 'application/json',
            dataType: 'json'
        })
            .always(function( msg ) {
                console.log("msg : " + msg);
                alert( "Data Saved: " + JSON.stringify(msg) );
                const id = '#' + resultTagId;
                console.log("id" + id);
                $(id).text(JSON.stringify(msg));
            });
    }
}
