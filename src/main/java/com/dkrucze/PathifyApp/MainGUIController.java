package com.dkrucze.PathifyApp;

import java.awt.image.BufferedImage;

import com.dkrucze.PathifyCore.ImageToPathConverter;
import com.dkrucze.PathifyCore.PathifiedImage;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
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
    private File inputFile;
    private BufferedImage image;
    private PathifiedImage result;
    private Animator animator;


    public void chooseImage(ActionEvent e){
        chooser.setTitle("Choose image");
        chooser.getExtensionFilters().add(imagesFilter);
        inputFile=chooser.showOpenDialog(canvas.getScene().getWindow());

        //If file is valid
        if(inputFile!=null){
            try {
                image = ImageIO.read(new File(inputFile.getAbsolutePath()));
                if(image.getWidth()<=10 || image.getHeight() <=10){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Image to small. \nImage need to have at lest 10x10 resolution.", ButtonType.OK);
                    alert.showAndWait();
                    computeButton.setDisable(true);
                    return;
                }
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
        Task<PathifiedImage> task = new Task<PathifiedImage>() {
            @Override
            protected PathifiedImage call() throws Exception {
                if(animator!=null)
                    animator.terminate();
                ImageToPathConverter converter = new ImageToPathConverter(image);
                result = converter.convert(progressBar);
                return result;
            }
        };

        task.setOnSucceeded(wse -> {
            progressBar.setProgress(1.0);
            computeButton.setDisable(true);
            animator = new Animator(canvas,result);
            animator.animate();
        });

        task.setOnFailed(wse -> {
            progressBar.setProgress(0);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong.", ButtonType.OK);
            alert.show();
        });

        new Thread(task).start();
    }
}
