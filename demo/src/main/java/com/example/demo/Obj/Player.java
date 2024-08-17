package com.example.demo.Obj;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v106.network.Network;
import org.openqa.selenium.devtools.v85.network.model.WebSocketFrame;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

public class Player {
    private Integer id;
    private String name_server;
    private String name;
    private Integer coin;
    private Integer type;
    private Integer selection;
    private Integer status;
    private Integer win_coin;
    private String time;

    public Player() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName_server() {
        return name_server;
    }

    public void setName_server(String name_server) {
        this.name_server = name_server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSelection() {
        return selection;
    }

    public void setSelection(Integer selection) {
        this.selection = selection;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWin_coin() {
        return win_coin;
    }

    public void setWin_coin(Integer win_coin) {
        this.win_coin = win_coin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Player(Integer id, String name_server, String name, Integer coin, Integer type, Integer selection, Integer status, Integer win_coin, String time) {
        this.id = id;
        this.name_server = name_server;
        this.name = name;
        this.coin = coin;
        this.type = type;
        this.selection = selection;
        this.status = status;
        this.win_coin = win_coin;
        this.time = time;
    }
}
