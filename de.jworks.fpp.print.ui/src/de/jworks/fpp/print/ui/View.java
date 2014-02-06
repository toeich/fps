package de.jworks.fpp.print.ui;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {

	public static final String ID = "de.jworks.fpp.print.ui.view";
	
	private Composite composite;
	private ScrollBar horizontalBar;
	private ScrollBar verticalBar;

	private float scale = 0.50f;
	private int offsetX = 0;
	private int offsetY = 0;

	private ImageData imageData;

	private float sW;

	private float sA;

	private float sD;

	@Override
	public void createPartControl(Composite parent) {

		try {
			PDDocument document = new PDDocument();
			
			PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page);
			
			PDFont font = PDType1Font.HELVETICA;
			sW = font.getStringWidth("Hello world.") * 24.0f / 1000.0f;
			sA = font.getFontBoundingBox().getUpperRightY() * 24.0f / 1000.0f;
			sD = font.getFontBoundingBox().getLowerLeftY() * 24.0f / 1000.0f;
			
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.beginText();
			contentStream.setFont(font, 24.0f);
			contentStream.setTextTranslation(100.0f, page.getMediaBox().getHeight() - 100.0f);
			contentStream.appendRawCommands(1 + " Tc\n");
			contentStream.appendRawCommands(100 + " Tz\n");
			contentStream.drawString("Hello world.");
			contentStream.endText();
			contentStream.close();
			
			BufferedImage bufferedImage = page.convertToImage();
			
			int newWidth = Math.round(bufferedImage.getWidth() * scale);
			int newHeight = Math.round(bufferedImage.getHeight() * scale);
			int mode = scale < 1 ? BufferedImage.SCALE_AREA_AVERAGING : BufferedImage.SCALE_SMOOTH;
			java.awt.Image image = bufferedImage.getScaledInstance(newWidth, newHeight, mode);
			bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(image, 0, 0, null);
			
			imageData = convertToSWT(bufferedImage);
			
			document.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		composite = new Composite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
		composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));
		composite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				paint(e.gc);
			}
		});
		composite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				updateScrollBars();
			}
		});

		horizontalBar = composite.getHorizontalBar();
		horizontalBar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite.redraw();
			}
		});

		verticalBar = composite.getVerticalBar();
		verticalBar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite.redraw();
			}
		});

		updateScrollBars();
	}

	@Override
	public void setFocus() {
	}

	public void updateScrollBars() {
		int width = 20 + imageData.width + 20;
		int height = 20 + imageData.height + 20;

		Rectangle clientArea = composite.getClientArea();

		horizontalBar.setMaximum(width);
		horizontalBar.setThumb(Math.min(clientArea.width, width));

		verticalBar.setMaximum(height);
		verticalBar.setThumb(Math.min(clientArea.height, height));

		composite.redraw();
	}

	private void paint(GC gc) {
		offsetX = 20 - horizontalBar.getSelection();
		offsetY = 20 - verticalBar.getSelection();

		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);

		gc.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		gc.setAlpha(127 / 10);
		for (int i = 0; i < 10; i++) {
			gc.fillRoundRectangle(offsetX - i, offsetY - i, imageData.width + 2 * i, imageData.height + 2 * i, 10, 10);
		}
		gc.setAlpha(255);

		Image image = new Image(gc.getDevice(), imageData);
		gc.drawImage(image, offsetX, offsetY);
		image.dispose();
		
		gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		gc.drawRectangle(offsetX + 100, Math.round(offsetY + 100 - sA), Math.round(sW + 12.0f), Math.round(sA - sD));
	}

	public static BufferedImage convertToAWT(ImageData data) {
		ColorModel colorModel = null;
		PaletteData palette = data.palette;
		if (palette.isDirect) {
			colorModel = new DirectColorModel(data.depth, palette.redMask, palette.greenMask, palette.blueMask);
			BufferedImage bufferedImage = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					RGB rgb = palette.getRGB(pixel);
					bufferedImage.setRGB(x, y,  rgb.red << 16 | rgb.green << 8 | rgb.blue);
				}
			}
			return bufferedImage;
		} else {
			RGB[] rgbs = palette.getRGBs();
			byte[] red = new byte[rgbs.length];
			byte[] green = new byte[rgbs.length];
			byte[] blue = new byte[rgbs.length];
			for (int i = 0; i < rgbs.length; i++) {
				RGB rgb = rgbs[i];
				red[i] = (byte)rgb.red;
				green[i] = (byte)rgb.green;
				blue[i] = (byte)rgb.blue;
			}
			if (data.transparentPixel != -1) {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue, data.transparentPixel);
			} else {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue);
			}		
			BufferedImage bufferedImage = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					pixelArray[0] = pixel;
					raster.setPixel(x, y, pixelArray);
				}
			}
			return bufferedImage;
		}
	}

	public static ImageData convertToSWT(BufferedImage bufferedImage) {
		if (bufferedImage.getColorModel() instanceof DirectColorModel) {
			DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
			PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask());
			ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int rgb = bufferedImage.getRGB(x, y);
					int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF)); 
					data.setPixel(x, y, pixel);
					if (colorModel.hasAlpha()) {
						data.setAlpha(x, y, (rgb >> 24) & 0xFF);
					}
				}
			}
			return data;		
		} else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
			IndexColorModel colorModel = (IndexColorModel)bufferedImage.getColorModel();
			int size = colorModel.getMapSize();
			byte[] reds = new byte[size];
			byte[] greens = new byte[size];
			byte[] blues = new byte[size];
			colorModel.getReds(reds);
			colorModel.getGreens(greens);
			colorModel.getBlues(blues);
			RGB[] rgbs = new RGB[size];
			for (int i = 0; i < rgbs.length; i++) {
				rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
			}
			PaletteData palette = new PaletteData(rgbs);
			ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
			data.transparentPixel = colorModel.getTransparentPixel();
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					data.setPixel(x, y, pixelArray[0]);
				}
			}
			return data;
		}
		return null;
	}

}