class TableUtils {
    constructor(tagName) {
        this.tagName = tagName;
    }

    /**
     * see this document
     * https://datatables.net/manual/server-side
     */
    getTable() {

        console.log("tag : " + '#' + this.tagName);

        $('#' + this.tagName).DataTable({
            // "processing": true,
            // "serverSide": true,

            "draw": 1,
            "ajax": {
             url : '/ajax/selectUser',
             method : "POST"
            },
            // "dataSrc":"",
            "columns":[
                {"data" : "id"},
                {"data" : "name"},
                {"data" : "description"},
                {"data" : "models"}
                ],
            "destroy": true
        })
    }
}