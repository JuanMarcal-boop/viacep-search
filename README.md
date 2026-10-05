# viacep-search

A Java console application that looks up Brazilian addresses by postal code (CEP) using the [ViaCEP](https://viacep.com.br) API and saves the results to a JSON file.

## Features

- Interactive menu: the user types a CEP and can keep searching until they choose to quit
- Input validation: a CEP must have exactly 8 digits, otherwise the API is never called
- Handles CEPs that have a valid format but don't exist (ViaCEP returns `"erro": "true"`)
- Handles network failures without crashing
- Saves every address found in the session to `address.json` as a JSON array

## Technologies

- Java 17
- `java.net.http` (`HttpClient`) for the HTTP requests
- [Gson](https://github.com/google/gson) 2.14.0 to convert between JSON and Java objects

## Project structure

```
src/
├── Main.java      # menu, validation, API request and file writing
└── Address.java   # model class that represents an address
```

## How to run

### Requirements

- JDK 17 or newer
- The [Gson jar](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.14.0/gson-2.14.0.jar) (version 2.14.0) saved in the project folder

### Compile and run

On Windows:

```bash
javac -cp gson-2.14.0.jar -d out src/*.java
java -cp "out;gson-2.14.0.jar" Main
```

On Linux or macOS:

```bash
javac -cp gson-2.14.0.jar -d out src/*.java
java -cp "out:gson-2.14.0.jar" Main
```

## Example

```
Digite um CEP: 01001000
Address{logradouro='Praça da Sé', bairro='Sé', cidade='São Paulo', uf='SP'}

Deseja consultar outro CEP? (sim/não)
não

Programa encerrado.
```

The generated `address.json`:

```json
[
  {
    "logradouro": "Praça da Sé",
    "bairro": "Sé",
    "localidade": "São Paulo",
    "uf": "SP"
  }
]
```

## Notes

- The program's messages are in Portuguese, since it is built around a Brazilian API.
- `address.json` is created again on every run, so results from a previous run are replaced.

## What I practiced

- Consuming a REST API with `HttpClient`
- Parsing and generating JSON with Gson
- Writing files safely with try-with-resources
- Validating input and handling different kinds of failure (invalid format, unknown CEP, network errors)

## Possible improvements

- Move the search logic out of `main` into its own class
- Check the HTTP status code before parsing the response
- Use Maven or Gradle to manage the Gson dependency
