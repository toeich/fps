//
// exportAsIDML.jsx
//

var myDocument = app.open("${inddFile}");
myDocument.exportFile(ExportFormat.INDESIGN_MARKUP, "${idmlFile}");
myDocument.close();
