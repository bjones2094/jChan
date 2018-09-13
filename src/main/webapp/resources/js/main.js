function confirmDelete(elementID, objectType) {
    if(confirm("Are you sure you want to delete this " + objectType + "?")) {
        document.getElementById(elementID).submit();
    }
}