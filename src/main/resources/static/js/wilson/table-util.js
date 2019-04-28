class TableUtils {
    constructor(tagName) {
        this.tagName = tagName;
    }

    getTable() {

        console.log("tag : " + '#' + this.tagName);

        $('#' + this.tagName).DataTable({
            "processing": true,
            "serverSide": true,
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