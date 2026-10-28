# Gelato v1.1.3

Gelato (former GelAnno or wbtools) is a Fiji/ImageJ plugin for traceable gel-figure assembly and paired kDa values mapping
Supported input: tif/tiff, png, jpg/jpeg

Version 1.1.3 suggests available source files during reconstruction and keeps reconstruction details available in a nonblocking window. Coordinate logs remain at format version 2, as introduced in 1.1.2.

Installation for windows: put gelato-x.x.x.jar file in "plugins" folder of Fiji and enjoy!

Installation for Mac users: open the file contents of Fiji by right-click. 
Drag gelato-x.x.x.jar to "plugins" directory. 
Open Fiji, choose "Plugins" from dropdown menu, select "Gelato".

Installation through Fiji Update site: 
Open Fiji. Click Help>Update..., then "Manage update sites". Click "Add Unlisted Site".
Fill the columns Name: GelAnno, URL: https://sites.imagej.net/GelAnno/.
Apply changes.

Works the same in ImageJ if iText module is present (used for PDF export).







# Basic workflow for separate marker/blot images

1. Click **Open kDa Marker Image...** and choose the ladder/marker (colorimetric) image.
2. Click each band in the marker image, and enter its label.
3. Click **Open Gel Image...** and choose the blot image.
4. Select crop for the figure by dragging, then press **Crop Region -> Figure**. The markers are transferred into the crop's local coordinates and included
   in every crop whose vertical range (in crop's local coordinates) contains them.

# Basic workflow for merged marker/blot images

1. Click **Open Gel Image...** and choose the merged image.
2. Click each band in the marker image, and enter its label.
3. Click **Stop marking kDa bands**.
4. Select crop for the figure by dragging, then press **Crop Region -> Figure**. The markers are transferred into the crop's local coordinates and included
   in every crop whose vertical range (in crop's local coordinates) contains them.

# Image reconstruction 

When the crops appear on the canvas, click **Show Coordinate Log** to view, copy, or save the used sources and coordinates. The first time, enter a **figure name**. Gelato remembers this name for the figure session and displays it in the canvas and coordinate-log window titles. Use **Rename Figure...** in the log window to change it. Canceling the naming prompt leaves the log unopened.

New logs use **log format version 2** with a dedicated figure-name field:

```text
Gelato Coordinate Log
Figure name: Figure 3B
Log format version: 2
Plugin version: 1.1.3
```

**Save Log** saves to the associated coordinate-log file. When there is no associated file, or when **Save Log As...** is selected, Gelato asks for a figure name (prefilled with the current name) and suggests `figure name.txt`. The file's name can differ from the figure name. Characters unsuitable for filenames are replaced only in the suggested filename; the internal figure name is preserved. Save As requires confirmation before replacing an existing file. After a successful save, ordinary Save Log updates the associated file directly.

The log window displays a snapshot. If the figure changes, a notice says that the log is out of date. Copying or saving offers **Refresh and Copy/Save**, **Copy/Save Displayed Log**, or **Cancel**. Refresh is the default; choosing the displayed log preserves its older coordinates. Renaming a figure updates the name without silently refreshing its coordinates. Additional annotations remain outside the coordinate-log format, including after Refresh.

The log is used to reconstruct the figure from declared sources and to highlight the used crops and marked kDa bands. 
Click "Reconstruct from Log..." and paste or load a log file ("Load Log..."). Click "Reconstruct". The program will prompt to load the declared images from the log file one by one. 

For both required gel images and optional marker-source images, if the logged file exists and is readable, Gelato opens its directory and preselects that file. Click **Use Image** to accept it, or browse to a replacement. Files are not loaded automatically, and the existing dimension checks still apply.

If the logged file is unavailable, the chooser opens the remembered last-used directory with no file selected. The existing **Required Gel image / Optional marker source**, **Expected path**, **Logged dimensions**, and **Used as** information remains unchanged; there is no extra missing-file message. The directory of an accepted image is remembered for later selections. Choosing another image after a load failure or dimension warning keeps the current browsing location instead of resetting to the suggested file.

Relative source paths are checked against the loaded coordinate-log file's folder. For example, `images/blot.tif` in `D:\Review\figure.txt` is looked up at `D:\Review\images\blot.tif`. This lookup does not edit the input log. Pasted logs have no folder against which to resolve relative paths, so those sources use manual selection. Absolute paths can be suggested for both loaded and pasted logs.

After all images are loaded, Gelato reconstructs the figure alongside annotated copies of the source images. The canvas title includes the restored figure name and **Reconstructed from coordinate log**. Source-window titles identify their filename, figure name, role, and position, for example:

```text
blot_A.tif - Used in Figure 3B - Crops (1/4)
ladder.tif - Used in Figure 3B - Markers MS-1 (2/4)
```

The count includes all annotated crop-source and marker-source windows actually opened in that reconstruction. The same file can have separate views for different roles. Renaming the figure updates these titles. Names are shown in window titles; no figure-name text is stamped onto source-image pixels or exported artwork.

**Reconstruction Details** opens in a separate, resizable window so you can inspect and interact with the figure and source images while reading it. **Keep on top** is selected by default on supported systems; turn it off to let other windows cover the report. Use the standard minimize button to fold the report away. Closing it hides the window; **Show Reconstruction Details** in the reconstructed figure's **Figure** section reopens it or restores it from minimized state. The report retains its scroll position and Keep on top choice. Its title follows figure renames, and closing the main figure also closes its report window. The report records the original reconstruction results and warnings; later figure edits do not rewrite it.

On the first **Save Log** after reconstruction from a loaded file, choose **Update Existing Log...**, **Save as New Log...**, or **Cancel**. Saving as new asks for a figure name and a different destination. Updating the existing log requires confirmation before replacement. After a successful save, subsequent saves use the associated file normally. Canceling or a failed save preserves the first-save choice. Reconstruction from pasted text has no associated file, so its first save asks for a name and destination.

Gelato continues to read format v1 and legacy logs. For a log without a figure name, it asks for one before reconstruction and suggests the loaded log's filename without its extension. Pasted unnamed logs start with an empty naming field. Format v2 requires a nonblank figure name on one line. Versions older than 1.1.2 cannot read format v2 logs; retain the original v1 file if older Gelato installations need it.

More information on coordinate log format in How it works.


# Adding annotations

After adding at least one crop on the A4 canvas, you can add figure annotations. Annotations are not logged or reconstructed. 

1. **Add sample labels**
Click anywhere on a crop to put a sample label above.
2. **Add band ticks**
Click anywhere on a crop and enter a band-specific label. The tick with the label appears on the right edge of the crop on the corresponding coordinate.
3. **Draw H-/V-line**
Draw horizontal or vertical lines by dragging.
4. **Edit Annotations**
Select any annotation exept for kDa markers to move, edit, resize text or delete. 



# Export

Exports the A4 cavnas to PDF. Everything (exept for the raster images) remains vector for publication or further editing. 

When **Use clip groups to group crop bands with their annotations** is selected during PDF export, the Form XObject structure is:

```text
Page
|- Band 1 Form
|  |- Raster crop image
|  |- Vector boundary
|  |- MW Markers Form
|     |- Ticks Form
|     `- kDa Values Form
|  |- Sample Labels Form
|  `- Band Ticks Form
|- Band 2 Form
|- Band Names Form
`- Free H/V line objects
```

This keeps every crop, its boundary, its sample labels, and its MW annotations under one PDF graphical object while keeping the band names separate. Illustrator imports the required Form XObject bounds as clipping masks; the symmetric bounds allow MW annotations to move between the left and right sides of a crop without being clipped. Leave the option unchecked to export the original flat structure with independently selectable objects and no band-level clip groups.



# How it works
Very simple.

Paired marker-blot Molecular weight annotation:
When kDa markers are registered by clicking on Marker image, the program stores its absolute coordinates (Xabs, Yabs) and the values, creating a "kDa markers set". 
When paired blot image is loaded, the kDa markers are shown on loaded image using the stored information.
Each crop of the blot image has its own local coordinate system that can be tilted relative to the original image. 
kDa markers that are falling into the crop's vertical boundaries in its local coordinate system are assigned to the crop with local coordinates (y_in_gel). 
When the crop is added to A4 canvas, Mw ticks are plotted on its edge using y_in_crop. 
If a new Marker image is loaded, new "kDa markers set" is created and applied to Gel images loaded afterwards.

Figure reconstruction:
Coordinate log is a human-readable text file with coordinate and source information of all used kDa marker sets and crops. 
Together with original images, it can be used to reconstruct the crops and kDa ticks, highlighting their positions on the images for facile audit of published figures.

Structure:
- **Figure and format information:** figure name, log-format version, and plugin version.
- **Coordinate convention:** the origin is the top-left pixel; X increases to the right, Y increases downward, and angles are measured clockwise.
- **kDa marker sets:** the source image, image dimensions, and absolute coordinates and labels of each marker. Only marker sets used by crops in the current figure are included.
- **Crops:** their order and band names, source image and dimensions, crop size, angle, origin, and all four corner coordinates.
- **Marker mapping:** the marker set used by each crop, coordinate scaling between images, and the exact position of every kDa tick within the crop.
- **Compatibility warnings:** shown when marker and gel images have different dimensions or aspect ratios.

In principle, anyone can create the log file manually. Gelato can reconstruct the images with either 3 corners OR size, angle, and origin of the crops, allowing for 1 pixel tolerance.
kDa information is optional (Global kDa marker sets, Used kDa markers). If used markers per band are not specified, all markers that apply are reconstructed by default.

# Implemented:

- Open TIFF, PNG, JPEG images and convert them to RGB for consistent rendering.
- Toggle kDa marker mode, click the source image, enter a kDa label, and store absolute image coordinates.
- Mirror kDa markers onto the Gel image, including synchronized label visibility, undo, and clear behavior.
- Switch safely between separate-marker-image and direct-on-Gel marker workflows.
- Preserve versioned kDa marker sets once crops use them.
- Show, copy, and save a coordinate log containing source paths, crop corners, marker mappings, and compatibility warnings. Only marker sets referenced by crops in the current figure are included.
- Reconstruct figures from pasted or loaded coordinate logs and user-supplied source images.
- Open one annotated source-image copy per reconstructed kDa marker set.
- Open each selected raw blot image once during reconstruction and overlay all of its crop boundaries, band labels, mapped marker coordinates, and crop-local connector lines to the left-edge tick positions. kDa labels move to the side of each X opposite its connectors.
- Export reconstructed annotated source windows as a full-resolution lossless PNG or as a hybrid PDF containing the source image as raster data and all visible overlay lines and text as vector objects.
- Draw an ordinary or rotated crop ROI in ImageJ/Fiji and add the cropped blot to a figure canvas.
- Project kDa marker coordinates into the rotated crop coordinate system, so tilted crops keep the molecular-weight ticks aligned.
- Show kDa ticks and labels on the left edge of each crop.
- Compose the figure on a fixed A4 portrait artboard, shown against a gray pasteboard with Fit, slider, +/- and Ctrl+mouse-wheel zoom controls. Zoom changes only the view.
- Drag crops on the figure canvas and resize the selected crop with the paired Narrower/Wider controls; ticks move and scale with the crop.
- Add crop-attached sample labels with a remembered session angle and font size. Horizontal labels center on the clicked lane; tilted labels begin at the clicked lane and rise to the right.
- Add crop-attached band ticks on the right edge. Their text can be edited and resized, and the complete tick can be moved vertically while remaining attached to its crop.
- Draw free horizontal and vertical vector lines by dragging. In Edit Annotations mode their bodies move and their endpoint handles change their length. Lines can be copied, pasted beside the original at the same height, and deleted.
- Select text through its visible bounds in Edit Annotations mode, including rotated sample labels; Shift-click supports multiple selection, and movable text can be dragged or nudged with the keyboard.
- Delete user-created sample labels, band ticks, band names, and free lines with Delete or Backspace. Calibrated kDa values and ticks remain protected.
- Resize selected text with A-/A+, or resize all figure text and future defaults when no text is selected. MW values are selectable for font resizing only. Font sizes are genuine typographic points on screen and in PDF.
- Export an A4 portrait PDF either as separate editable objects (the default) or with an optional Form XObject per band containing the raster crop, vector boundary, crop-attached sample labels, and a nested MW-marker Form XObject with separate tick and kDa-value forms. Raster DPI changes only embedded crop resolution; page geometry and vector text stay fixed. Group bounds include editing room on both sides of the crop, and neither mode paints a page background.
Annotation edits are runtime figure state. They are included in PDF export but intentionally are not written to or reconstructed from coordinate logs.
New coordinate logs use the `Gelato Coordinate Log` header and log format version 2. Gelato also accepts the legacy `GelAnno Coordinate Log` and `WB Tool Coordinate Log` headers and older log versions.
- **Ctrl+Z** to undo the last figure edit and **Ctrl+Y** to redo
- Alignment and distribution: multi-select elements with shift and choose alignment. Markers stay in place.


# Citation

If you use Gelato to prepare figures for a publication, please cite the version of the tool that you used.

Maria A. Pirozhkova, Elisheva Babitz. Gelato: an ImageJ/Fiji plugin for traceable gel figures assembly and annotation. GitHub repository: https://github.com/masha-rgfj/Gelato-fiji-plugin 2026


## Contributions

- Maria A. Pirozhkova — concept, software development, implementation, maintenance
- Elisheva Babitz — documentation, user manuals, installation support, testing, user onboarding, and dissemination


# Licence
AGPL-3

# Contact
pirozhkova@mail.tau.ac.il
