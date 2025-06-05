-- Tabela Patient (herda campos de Person)
CREATE TABLE patient (
                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                         name TEXT NOT NULL,
                         phone_number TEXT NOT NULL UNIQUE,
                         email TEXT NOT NULL UNIQUE,
                         cpf TEXT NOT NULL UNIQUE,
                         ra TEXT UNIQUE,
                         birth_year TEXT NOT NULL,
                         gender TEXT NOT NULL,
                         occupation TEXT,
                         education_level TEXT,
                         type TEXT NOT NULL,
                         address TEXT NOT NULL
);

-- Tabela Dependent (herda campos de Person + campos específicos)
CREATE TABLE dependent (
                           id INTEGER PRIMARY KEY AUTOINCREMENT,
                           name TEXT NOT NULL,
                           phone_number TEXT NOT NULL UNIQUE,
                           email TEXT NOT NULL UNIQUE,
                           relationship TEXT,
                           patient_id INTEGER NOT NULL,
                           FOREIGN KEY (patient_id) REFERENCES patient(id)
);

-- Índices para melhorar performance
CREATE UNIQUE INDEX idx_patient_email ON patient(email);
CREATE UNIQUE INDEX idx_patient_phone ON patient(phone_number);
CREATE UNIQUE INDEX idx_patient_cpf ON patient(cpf);
CREATE UNIQUE INDEX idx_dependent_email ON dependent(email);
CREATE UNIQUE INDEX idx_dependent_phone ON dependent(phone_number);