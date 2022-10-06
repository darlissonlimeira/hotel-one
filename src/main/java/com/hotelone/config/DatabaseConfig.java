package com.hotelone.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelone","root","1234");
        } catch (SQLException ex) {
            return null;
        }
    }
}

//Queries

//create table hospede(
//       id VARCHAR(255) PRIMARY KEY NOT NULL,
//       nome VARCHAR(50) NOT NULL,
//       sobrenome VARCHAR(50) NOT NULL,
//       data_nascimento DATE NOT NULL,
//       telefone VARCHAR(20) NOT NULL,
//       nacionalidade VARCHAR(100) NOT NULL);

//create table reserva (id VARCHAR(255) PRIMARY KEY NOT NULL, checkin DATE NOT NULL, checkout DATE NOT NULL, forma_pagamento VARCHAR(100), valor DECIMAL NOT NULL, CHECK (valor>0), hospede_id VARCHAR(255), FOREIGN KEY (hospede_id) REFERENCES hospede(id) ON DELETE CASCADE ON UPDATE CASCADE);

//insert into hospede values ('c9a207c3-cd03-48f8-b747-ce3a49497d52',
//                                    'Darlisson',
//                                    'Limeira',
//                                    '1993-01-11',
//                                    '+5599988151557',
//                                    'brasileiro');

//insert into hospede values ('f4312631-8ad9-4034-872a-362fed4e0a7e',
//                                    'Hellen Pabline',
//                                    'Leal', '1993-09-06',
//                                    '+5599981658789',
//                                    'brasileiro');

//insert into hospede values ('012b7496-4142-4975-970b-9da499f019aa',
//                                    'Fulano',
//                                    'Gringo',
//                                    '1973-10-26',
//                                    '+2299981658789',
//                                    'argentino');
//
//
//insert into reserva values (
//      '65438845-4b7e-45c7-8b00-3a13ead00d2e',
//      '2022-10-01',
//      '2022-10-15',
//      'BOLETO',
//      1800.00,
//      '012b7496-4142-4975-970b-9da499f019aa');





