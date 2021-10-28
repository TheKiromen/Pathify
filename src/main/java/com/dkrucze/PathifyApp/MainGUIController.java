package com.dkrucze.PathifyApp;

import java.awt.image.BufferedImage;

import com.dkrucze.PathifyCore.ImageToPathConverter;
import com.dkrucze.PathifyCore.PathifiedImage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MainGUIController {

    //GUI components
    @FXML Button loadFile;
    @FXML Button computeButton;
    @FXML Canvas canvas;
    @FXML ProgressBar progressBar;
    @FXML Label fileName;

    //Variables
    FileChooser chooser = new FileChooser();
    //FIXME Add more file extensions
    FileChooser.ExtensionFilter imagesFilter = new FileChooser.ExtensionFilter("Images","*.jpg","*.png");
    File inputFile;
    BufferedImage image;
    PathifiedImage result;


    public void chooseImage(ActionEvent e){
        chooser.setTitle("Choose image");
        chooser.getExtensionFilters().add(imagesFilter);
        inputFile=chooser.showOpenDialog(canvas.getScene().getWindow());

        //If file is valid
        if(inputFile!=null){
            try {
                image = ImageIO.read(new File(inputFile.getAbsolutePath()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            computeButton.setDisable(false);
            progressBar.setDisable(false);
            progressBar.setProgress(0);
            fileName.setText(inputFile.getName());
        }
    }

    public void processImage(ActionEvent e){
        progressBar.setProgress(-1.0);
        try{
            //TODO run this on seperate thread to not clog main FX thread
            ImageToPathConverter converter = new ImageToPathConverter(image);
            result = converter.convert();
        }catch(IllegalArgumentException ex){
            ex.printStackTrace();
            progressBar.setProgress(0);
        }
        computeButton.setDisable(true);
        progressBar.setProgress(1.0);
    }


}