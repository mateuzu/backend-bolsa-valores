package com.spring.agregadorinvestimentos.controller.dto;
/*
 * DTO = Data Transfer Object - Objeto para transferência de dados de uma camada para outra
 * È possível definir os campos que serão recebido na requisição para criar um User
 */
public record CreateUserDto(String userName, String email, String password) {

}
