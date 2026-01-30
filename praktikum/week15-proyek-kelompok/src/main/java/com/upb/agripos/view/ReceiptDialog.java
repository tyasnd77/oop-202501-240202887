package com.upb.agripos.view;

import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Dialog untuk menampilkan struk transaksi.
 */
public class ReceiptDialog {
    private Stage stage;
    private TextArea receiptArea;
    private Button printButton;
    private Button closeButton;

    public ReceiptDialog(Stage owner) {
        this.stage = new Stage();
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initOwner(owner);
        this.stage.setTitle("Struk Transaksi");
        this.stage.setWidth(500);
        this.stage.setHeight(600);
    }

    public void buildUI(String receiptContent) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f9fafb;");

        // Title
        Label titleLabel = new Label("STRUK TRANSAKSI");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #10b981;");

        // Receipt Content
        receiptArea = new TextArea(receiptContent);
        receiptArea.setWrapText(true);
        receiptArea.setEditable(false);
        receiptArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11; -fx-text-fill: #1f2937; -fx-control-inner-background: white;");

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setStyle("-fx-background-color: #f0fdf4; -fx-border-color: #dcfce7; -fx-border-radius: 5;");
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        printButton = new Button("🖨️  Cetak");
        printButton.setPrefWidth(120);
        printButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");
        printButton.setOnMouseEntered(e -> printButton.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;"));
        printButton.setOnMouseExited(e -> printButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;"));
        printButton.setOnAction(e -> printReceipt());

        closeButton = new Button("❌  Tutup");
        closeButton.setPrefWidth(120);
        closeButton.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-padding: 10; -fx-font-weight: bold;"));
        closeButton.setOnAction(e -> stage.close());

        buttonBox.getChildren().addAll(printButton, closeButton);

        root.getChildren().addAll(titleLabel, new Separator(), receiptArea, buttonBox);
        VBox.setVgrow(receiptArea, Priority.ALWAYS);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public Button getPrintButton() {
        return printButton;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    private void printReceipt() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(stage)) {
            printerJob.printPage(receiptArea);
            printerJob.endJob();
        }
    }
}
