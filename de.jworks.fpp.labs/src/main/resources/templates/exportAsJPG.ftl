//
// exportAsJPG.jsx
//

var myDocument = app.open("${inddFile}");
app.jpegExportPreferences.jpegQuality = JPEGOptionsQuality.MAXIMUM;
app.jpegExportPreferences.exportResolution = 96;
app.jpegExportPreferences.exportingSpread = true;
myDocument.exportFile(ExportFormat.PDF_TYPE, "${jpgFile}");
myDocument.close();
