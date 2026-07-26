package com.jociel.estoque.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.function.Consumer;

public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * TÉCNICA 1 (a mais usada para navegação principal):
     * Troca apenas o conteúdo (root) da Scene, mantendo a mesma janela (Stage).
     * É rápido, não pisca, e mantém o tamanho/posição da janela.
     * Ideal para: Login -> Menu -> Estoque -> Relatório etc.
     */
    public void trocarTela(String caminhoFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFxml));
            Parent novoRoot = loader.load();

            Scene scene = primaryStage.getScene();
            if (scene == null) {
                scene = new Scene(novoRoot);
                primaryStage.setScene(scene);
            } else {
                scene.setRoot(novoRoot);
            }

            primaryStage.setTitle(titulo);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void voltarTela(ActionEvent event, String caminho, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * TÉCNICA 2 (para formulários e caixas de diálogo):
     * Abre uma tela nova em uma janela (Stage) separada, do tipo MODAL.
     * "Modal" significa que o usuário não consegue clicar na janela anterior
     * enquanto essa não for fechada. Muito usado em telas de
     * "Adicionar produto" / "Editar produto".
     *
     * O método devolve o controller da tela aberta, para que possamos
     * consultar dados que o usuário preencheu (ex: o produto criado).
     */
    public <T> T abrirModal(String caminhoFxml, String titulo) {
        return abrirModal(caminhoFxml, titulo, null);
    }

    /**
     * Mesma ideia acima, mas permite passar um "antesDeExibir" para configurar
     * o controller (ex: preencher campos para edição) ANTES da janela travar
     * a execução com showAndWait().
     */
    public <T> T abrirModal(String caminhoFxml, String titulo, Consumer<T> antesDeExibir) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFxml));
            Parent root = loader.load();
            T controller = loader.getController();

            if (antesDeExibir != null) {
                antesDeExibir.accept(controller);
            }

            Stage modalStage = new Stage();
            modalStage.setTitle(titulo);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(primaryStage);
            modalStage.setScene(new Scene(root));
            modalStage.setResizable(false);

            // showAndWait() PAUSA o código aqui até a janela modal ser fechada
            modalStage.showAndWait();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * TÉCNICA 3 (menos usada, mas importante conhecer):
     * Fecha a janela atual (Stage) por completo e abre uma nova no lugar.
     * Diferente da Técnica 1 porque cria uma Stage nova (nova janela do
     * sistema operacional). Pode ser útil, por exemplo, se cada tela
     * precisar de tamanhos/posições de janela bem diferentes.
     * Para a maioria dos sistemas simples de estoque, a Técnica 1 é melhor.
     */
    public void trocarTelaNovaJanela(String caminhoFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFxml));
            Parent root = loader.load();

            Stage janelaAtual = primaryStage;
            Stage novaJanela = new Stage();
            novaJanela.setTitle(titulo);
            novaJanela.setScene(new Scene(root));

            setPrimaryStage(novaJanela);
            novaJanela.show();
            janelaAtual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

