function confirmDelete(elementID, objectType) {
    if(confirm("Are you sure you want to delete this " + objectType + "?")) {
        document.getElementById(elementID).submit();
    }
}

function unhideElement(elementID) {
    document.getElementById(elementID).hidden = false;
}

function submitForm(elementID) {
    document.getElementById(elementID).submit();
}