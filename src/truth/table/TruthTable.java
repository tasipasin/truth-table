package truth.table;

import java.util.Scanner;

/**
 * Classe para resolução de Tabelas Verdade de até 3 literais.
 */
public class TruthTable {

    /** Define o caracter correspondente ao valor booleano verdadeiro. */
    private static final Character TRUE_VALUE = 'V';
    /** Define o caracter correspondente ao valor booleano false. */
    private static final Character FALSE_VALUE = 'F';

    /**
     * Retorna o caracter correspondente ao valor booleano.
     * @param p Variável booleana para ser avaliada.
     * @return o caracter correspondente ao valor booleano.
     */
    private char printBit(boolean p) {
        return p ? TRUE_VALUE : FALSE_VALUE;
    }

    /**
     * Verifica se o caracter de referência está no índice informado da fórmula.
     * @param formula Fórmula completa.
     * @param index Índice.
     * @param reference Caracter de referência.
     * @return Verdadeiro para caracter de referência no índice informado.
     */
    private boolean compareFormulaChar(String formula, int index, char reference) {
        return formula.charAt(index) == reference;
    }

    /**
     * Verifica se na posição informada da fórmula existe um operador.
     * @param formula Fórmula completa.
     * @param index Índice.
     * @return Verdadeiro para operador na posição indicada.
     */
    private boolean isOperator(String formula, int index) {
        return compareFormulaChar(formula, index, '^')
                || compareFormulaChar(formula, index, 'v');
    }

    /**
     * Verifica se a fórmula está formatada para uma variável.
     * @param formula Fórmula inserida.
     * @return Verdadeiro para fórmula formatada para uma variável.
     */
    private boolean find1VariableFormula(String formula) {
        // Verifica se a fórmula inserida é p ou ~p
        boolean result = (this.compareFormulaChar(formula, 0, '~')
                && this.compareFormulaChar(formula, 1, 'p'))
                || (this.compareFormulaChar(formula, 0, 'p')
                && formula.length() == 1);
        // Verifica se a fórmula está formatada corretamente
        if (result) {
            // executa processo para resolução e impressão da tabela verdade da fórmula
            this.oneLiteralTable(formula);
        }
        return result;
    }

    /**
     * Verifica se a fórmula recebida possui 2 variáveis e encaminha para
     * avaliação.
     * @param formula Fórmula recebida.
     * @return Verdadeiro para fórmula bem formada.
     */
    private boolean find2VariableFormula(String formula) {
        // Inicia a variável de resultado de formula bem formada como falso
        boolean result = false;
        // Recupera o tamanho da fórmula
        int formulaLenght = formula.length();
        // Executa o código do tamanho da fórmula
        switch (formulaLenght) {
            case 3:
                // Verifica se o primeiro caracter é o literal p
                // se o terceiro é o literal q e se há um operador entre os dois
                result = this.compareFormulaChar(formula, 0, 'p')
                        && this.compareFormulaChar(formula, 2, 'q')
                        && this.isOperator(formula, 1);
                break;
            case 4:
                // Verifica se a fórmula inicia com uma negação
                if (this.compareFormulaChar(formula, 0, '~')) {
                    // Verifica se o segundo caracter é o literal p
                    // o quarto o literal q e se há um operador entre os literais
                    result = this.compareFormulaChar(formula, 1, 'p')
                            && this.compareFormulaChar(formula, 3, 'q')
                            && this.isOperator(formula, 2);
                } // Verifica se podem ser as fórmulas p^~q ou pv~q
                else if (this.compareFormulaChar(formula, 0, 'p')) {
                    result = this.compareFormulaChar(formula, 2, '~')
                            && this.compareFormulaChar(formula, 3, 'q')
                            && this.isOperator(formula, 1);
                }
                break;
            case 5:
                // Testa se a fórmula está correta -> ~p^~q
                result = this.compareFormulaChar(formula, 0, '~')
                        && this.compareFormulaChar(formula, 1, 'p')
                        && this.compareFormulaChar(formula, 3, '~')
                        && this.compareFormulaChar(formula, 4, 'q')
                        && this.isOperator(formula, 2);
                break;
            default:
                // Se o tamanho da fórmula for maior do que o previsto,
                // provavelmente está errada
                break;
        }
        // Verifica se a fórmula é bem formada
        if (result) {
            // Encaminha para avaliação
            twoLiteralTable(formula);
        }
        return result;
    }

    /**
     * Verifica se a fórmula recebida possui 3 variáveis e encaminha para
     * avaliação.
     * @param formula Fórmula recebida.
     * @return Verdadeiro para fórmula bem formada.
     */
    private boolean find3VariableFormula(String formula) {
        boolean result = false;
        int formulaLenght = formula.length();
        switch (formulaLenght) {
            case 5:
                // Verifica se o primeiro caracter é o literal p,
                // se o terceiro é o literal q, se o quinto é o literal r
                // e se há operador entre cada um dos três
                result = this.compareFormulaChar(formula, 0, 'p')
                        && this.compareFormulaChar(formula, 2, 'q')
                        && this.compareFormulaChar(formula, 4, 'r')
                        && this.isOperator(formula, 1)
                        && this.isOperator(formula, 3);
                break;
            case 6:
                // Verifica se o primeiro literal está negado
                if (this.compareFormulaChar(formula, 0, '~')) {
                    // Verifica se o literal p está na segunda posição,
                    // se o literal q esta na quarta posição
                    // se o literal r esta na sexta posição
                    // e se existem operadores entres os literais
                    result = this.compareFormulaChar(formula, 1, 'p')
                            && this.compareFormulaChar(formula, 3, 'q')
                            && this.compareFormulaChar(formula, 5, 'r')
                            && this.isOperator(formula, 2)
                            && this.isOperator(formula, 4);
                    // Verifica se o segundo literal está negado
                } else if (this.compareFormulaChar(formula, 2, '~')) {
                    result = this.compareFormulaChar(formula, 0, 'p')
                            && this.compareFormulaChar(formula, 3, 'q')
                            && this.compareFormulaChar(formula, 5, 'r')
                            && this.isOperator(formula, 1)
                            && this.isOperator(formula, 4);
                    // Verifica se o terceiro literal está negado
                } else if (this.compareFormulaChar(formula, 4, '~')) {
                    result = this.compareFormulaChar(formula, 0, 'p')
                            && this.compareFormulaChar(formula, 2, 'q')
                            && this.compareFormulaChar(formula, 5, 'r')
                            && this.isOperator(formula, 1)
                            && this.isOperator(formula, 3);
                }
                break;
            case 7:
                // Verifica se o primeiro literal está negado,
                // o terceiro literal não e se há um operador entre os literais
                if (this.compareFormulaChar(formula, 0, '~')
                        && this.compareFormulaChar(formula, 1, 'p')
                        && isOperator(formula, 2)
                        && this.compareFormulaChar(formula, 6, 'r')) {
                    // Verifica se o literal q esta na quinta posição, e negado
                    // na posição anterior e existe um operador entre ele e o 
                    // literal r, ou se o literal q esta na quarta posição,
                    // o literal r está negado na posição anterior e se há operadores
                    // entre os literais
                    result = (this.compareFormulaChar(formula, 3, '~')
                            && this.compareFormulaChar(formula, 4, 'q')
                            && this.isOperator(formula, 5))
                            || (this.compareFormulaChar(formula, 3, 'q')
                            && this.isOperator(formula, 4)
                            && this.compareFormulaChar(formula, 5, '~'));
                } else {
                    // Verifica que o primeiro literal não está negado
                    // o literal q está na quarta posição, negado na posição anterior
                    // o literal r também e se existem operadores entre os literais
                    result = this.compareFormulaChar(formula, 0, 'p')
                            && this.isOperator(formula, 1)
                            && this.compareFormulaChar(formula, 2, '~')
                            && this.compareFormulaChar(formula, 3, 'q')
                            && this.isOperator(formula, 4)
                            && this.compareFormulaChar(formula, 5, '~')
                            && this.compareFormulaChar(formula, 6, 'r');
                }
                break;
            case 8:
                result = this.compareFormulaChar(formula, 0, '~')
                        && this.compareFormulaChar(formula, 1, 'p')
                        && this.isOperator(formula, 2)
                        && this.compareFormulaChar(formula, 3, '~')
                        && this.compareFormulaChar(formula, 4, 'q')
                        && this.isOperator(formula, 5)
                        && this.compareFormulaChar(formula, 6, '~')
                        && this.compareFormulaChar(formula, 7, 'r');
                break;
            default:
                // Se o tamanho da fórmula for maior do que o previsto,
                // provavelmente está errada
                break;
        }
        // Verifica se a fórmula é bem formada
        if (result) {
            // Encaminha para avaliação
            threeLiteralTable(formula);
        }
        return result;
    }

    /**
     * Realiza comparações lógicas para um literal.
     * @param formula Fórmula inserida.
     */
    public void oneLiteralTable(String formula) {
        char[] literals = {'p'};
        // Imprime cabeçalho da tabela verdade
        printHeader(literals, formula);
        // Percorre os literais
        for (int i = 1; i >= 0; i--) {
            // Inicializa os literais e a resposta
            boolean p, resp;
            // Casos
            switch (i) {
                // Para o primeiro caso
                case 1:
                    p = true;
                    break;
                // Caso padrão
                default:
                    p = false;
            }
            // Verifica se está negado e executa comparação do literal
            resp = this.compareFormulaChar(formula, 0, '~') ? !p : p;
            // Imprime a linha de literais e resposta
            System.out.println(String.format("| %c | %c", printBit(p), printBit(resp)));
        }
        // Imprime o traçado do cabeçalho
        TruthTable.printHeaderSpace(literals.length, formula.length());
    }

    /**
     * Realiza comparações lógicas para dois literais.
     * @param formula Fórmula inserida.
     */
    public void twoLiteralTable(String formula) {
        char[] literals = {'p', 'q'};
        // Imprime cabeçalho da tabela verdade
        printHeader(literals, formula);
        // Percorre a quantidade de possibilidades para a quantidade de literais 2^n
        for (int i = 3; i >= 0; i--) {
            // Inicializa os literais e a resposta
            boolean p, q, resp = true;
            // Troca das possibilidades
            switch (i) {
                // Primeira possibilidade
                case 3:
                    // Assume os valores como verdade
                    p = q = true;
                    break;
                // Segunda possibilidade
                case 2:
                    // Assume um literal como verdadeiro e outro como falso
                    p = true;
                    q = !p;
                    break;
                // Terceira possibilidade
                case 1:
                    // Assume um literal como falso e outro como verdadeiro
                    p = false;
                    q = !p;
                    break;
                // Quarta possibilidade
                default:
                    p = q = false;
            }
            // Recupera o tamanho da fórmula
            int formulaLenght = formula.length();
            // Troca de casos da fórmula
            switch (formulaLenght) {
                // Se o tamanho da fórmula for 3, significa que não existe a negação no primeiro ou segundo literal
                case 3:
                    // Verifica se a operação lógica é AND ou OR
                    resp = this.compareFormulaChar(formula, 1, '^') ? p && q : p || q;
                    break;
                // Se o tamanho da fórmula for 4, significa que existe a negação no primeiro ou segundo literal  
                case 4:
                    // Verifica se a negação está no primeiro literal
                    if (this.compareFormulaChar(formula, 0, '~')) {
                        // Verifica se a operação lógica é AND ou OR
                        resp = this.compareFormulaChar(formula, 2, '^') ? (!p && q) : (!p || q);
                        // Verifica se a negação está no segundo literal
                    } else if (this.compareFormulaChar(formula, 2, '~')) {
                        // Verifica se a operação lógica é AND ou OR
                        resp = this.compareFormulaChar(formula, 1, '^') ? (p && !q) : (p || !q);
                    }
                    break;
                default:
                    // Se não cair em outro caso, significa que a fórmula tem tamanho 5
                    // nesse caso, os dois literais estão sendo negados
                    // então só é necessário verificar se é operação lógica AND ou OR
                    resp = this.compareFormulaChar(formula, 2, '^') ? (!p && !q) : (!p || !q);
                    break;
            }
            System.out.println(String.format("| %c | %c | %c", printBit(p), printBit(q), printBit(resp)));
        }
        // Imprime o traçado do rodapé
        TruthTable.printHeaderSpace(literals.length, formula.length());
    }

    /**
     * Realiza comparações lógicas para três literais.
     * @param formula Fórmula inserida.
     */
    public void threeLiteralTable(String formula) {
        char[] literals = {'p', 'q', 'r'};
        // Imprime o cabeçalho
        printHeader(literals, formula);
        // Percorre as opções de valores para os literias
        for (int i = 7; i >= 0; i--) {
            boolean p, q, r, resp = true;
            switch (i) {
                case 7:
                    p = q = r = true;
                    break;
                case 6:
                    p = q = true;
                    r = !q;
                    break;
                case 5:
                    p = true;
                    q = !p;
                    r = !q;
                    break;
                case 4:
                    p = true;
                    q = !p;
                    r = q;
                    break;
                case 3:
                    p = false;
                    q = !p;
                    r = q;
                    break;
                case 2:
                    p = false;
                    q = !p;
                    r = !q;
                    break;
                case 1:
                    p = q = false;
                    r = !q;
                    break;
                default:
                    p = q = r = false;
            }
            // Recupera o tamanho da fórmula
            int formulaLenght = formula.length();
            // Muda para a opção do tamanho da fórmula
            switch (formulaLenght) {
                case 5:
                    // Verifica se o primeiro operador é AND
                    if (this.compareFormulaChar(formula, 1, '^')) {
                        // Verifica se o segundo operador é AND
                        resp = this.compareFormulaChar(formula, 3, '^') ? p && q && r : p && q || r;
                        // Verifica se o primeiro operador é OR
                    } else {
                        // Verifica se o segundo operador é AND
                        resp = this.compareFormulaChar(formula, 3, '^') ? p || q && r : p || q || r;
                    }
                    break;
                case 6:
                    // Verifica se o primeiro literal está negado
                    if (this.compareFormulaChar(formula, 0, '~')) {
                        // Verifica se o primeiro operador é AND
                        if (this.compareFormulaChar(formula, 2, '^')) {
                            // Verifica se o segundo operador é AND ou OR e realiza a avaliação da fórmula
                            resp = this.compareFormulaChar(formula, 4, '^') ? !p && q && r : !p && q || r;
                        } else {
                            // O primeiro operador é OR
                            // Verifica se o segundo operador é AND ou OR e realiza a avaliação da fórmula
                            resp = this.compareFormulaChar(formula, 4, '^') ? !p || q && r : !p || q || r;
                        }
                        // Verifica se o segundo literal está negado
                    } else if (this.compareFormulaChar(formula, 2, '~')) {
                        // Verifica se o primeiro operador é AND
                        if (this.compareFormulaChar(formula, 1, '^')) {
                            // Verifica se o segundo operador é AND ou OR
                            resp = this.compareFormulaChar(formula, 4, '^') ? p && !q && r : p && !q || r;
                        } else {
                            // O primeiro operador é OR
                            // Verifica se o segundo operador é AND ou OR
                            resp = this.compareFormulaChar(formula, 4, '^') ? p || !q && r : p || !q || r;
                        }
                        // Verifica se o terceiro literal está negado
                    } else if (this.compareFormulaChar(formula, 4, '~')) {
                        // Verifica se o primeiro operador é AND
                        if (this.compareFormulaChar(formula, 1, '^')) {
                            // Verifica se o segundo operador é AND ou OR
                            resp = this.compareFormulaChar(formula, 3, '^') ? p && q && !r : p && q || !r;
                        }
                    } else {
                        // O segundo operador é OR
                        // Verifica se o segundo operador é AND ou OR
                        resp = this.compareFormulaChar(formula, 3, '^') ? p || q && !r : p || q || !r;
                    }
                    break;
                case 7:
                    // Verifica se o primeiro literal está negado
                    if (this.compareFormulaChar(formula, 0, '~')) {
                        // Verifica se o segundo literal está negado
                        if (this.compareFormulaChar(formula, 3, '~')) {
                            // Verifica se o primeiro operador é AND
                            if (this.compareFormulaChar(formula, 2, '^')) {
                                // Verifica se o segundo operador é AND ou OR
                                resp = this.compareFormulaChar(formula, 5, '^') ? !p && !q && r : !p && !q || r;
                            } else {
                                // O segundo operador é OR
                                // Verifica se o segundo operador é AND ou OR
                                resp = this.compareFormulaChar(formula, 5, '^') ? !p && !q || r : !p || !q || r;
                            }
                            // Verifica se o terceiro literal está negado
                        } else if (this.compareFormulaChar(formula, 5, '~')) {
                            // Verifica se o primeiro operador é AND
                            if (this.compareFormulaChar(formula, 2, '^')) {
                                // Verifica se o segundo operador é AND ou OR
                                resp = this.compareFormulaChar(formula, 4, '^') ? !p && q && !r : !p && q || !r;
                            } else {
                                // O segundo operador é OR
                                // Verifica se o segundo operador é AND ou OR
                                resp = this.compareFormulaChar(formula, 4, '^') ? !p && q || !r : !p || q || !r;
                            }
                        }
                    } else {
                        // O primeiro literal não está negado
                        // Verifica se o primeiro operador é AND
                        if (this.compareFormulaChar(formula, 1, '^')) {
                            resp = this.compareFormulaChar(formula, 4, '^') ? p && !q && !r : p && !q || !r;
                        } else {
                            // O primeiro operador é OR
                            resp = this.compareFormulaChar(formula, 4, '^') ? p || !q && !r : p || !q || !r;
                        }
                    }
                    break;
                default:
                    // Verifica se o primeiro operador é AND
                    if (this.compareFormulaChar(formula, 2, '^')) {
                        // Verifica se o segundo operador é AND ou OR
                        resp = this.compareFormulaChar(formula, 5, '^') ? !p && !q && !r : !p && !q || !r;
                    } else {
                        // O segundo operador é OR
                        // Verifica se o segundo operador é AND ou OR
                        resp = this.compareFormulaChar(formula, 5, '^') ? !p || !q && !r : !p || !q || !r;
                    }
                    break;
            }
            // Imprime a saída da combinação atual de valores
            System.out.println(String.format("| %c | %c | %c | %c", printBit(p),
                    printBit(q), printBit(r), printBit(resp)));
        }
        // Imprime o traçado do cabeçalho
        TruthTable.printHeaderSpace(literals.length, formula.length());
    }

    /**
     * Imprime o cabeçalho da tabela verdade.
     * @param literals Array de Literais.
     * @param formula Fórmula.
     */
    private void printHeader(char[] literals, String formula) {
        // Imprime o traçado do cabeçalho
        TruthTable.printHeaderSpace(literals.length, formula.length());
        // Inicia um StringBuilder
        StringBuilder sb = new StringBuilder();
        // Adiciona o separador das variáveis
        sb.append("|");
        // Adiciona a variável, em ordem, com o separador
        for (char variable : literals) {
            sb.append(String.format(" %s |", variable));
        }
        // Imprime as variáveis
        System.out.print(sb.toString());
        // Imprime a fórmula
        System.out.println(String.format(" (%s) ", formula));
        // Imprime o traçado do cabeçalho
        TruthTable.printHeaderSpace(literals.length, formula.length());
    }

    /**
     * Imprime o traçado do cabeçalho.
     * @param literalsLength Quantidade de variáveis.
     * @param formulaSize Tamanho da fórmula.
     */
    private static void printHeaderSpace(int literalsLength, int formulaSize) {
        // Imprime o espaço das variáveis
        for (int i = 0; i < literalsLength; i++) {
            System.out.print("+---");
        }
        System.out.print("+");
        // Recupera o tamanho da fórmula e soma com mais 2 para compensar os parênteses
        int formulaSizePlusParenthesis = formulaSize + 2;
        // Imprime o espaço da fórmula
        for (int i = 0; i < formulaSizePlusParenthesis; i++) {
            System.out.print("-");
        }
        System.out.println("-+");
    }

    /**
     * Função inicial/principal da classe.
     * @param argv Argumentos.
     */
    public static void main(String[] argv) {
        // Inicializa varíavel que vai receber a fórmula
        String formula;
        // Inicializa indicador de fórmula verdadeira
        boolean tabOk = false;
        try {
            // Inicializa objeto de leitura
            Scanner sc = new Scanner(System.in);
            // Imprime no console informação para esperar entrada do usuário
            System.out.print("Entre com a fórmula: ");
            // Realiza leitura da fórmula
            formula = sc.nextLine();
            // Fecha o objeto de leitura
            sc.close();
            // Troca todos os espaços em branco por nada
            formula = formula.toLowerCase().replace(" ", "");
            // Inicializa objeto da tabela verdade
            TruthTable tt = new TruthTable();
            // Verifica o tamanho da fórmula
            switch (formula.length()) {
                case 1:
                // Qualquer formula de tamanho 1 é uma fórmula de 1 literal
                case 2:
                    tabOk = tt.find1VariableFormula(formula);
                    break;
                case 3:
                // Qualquer formula de tamanho 3 é uma fórmula de 2 literais
                case 4:
                    tabOk = tt.find2VariableFormula(formula);
                    break;
                case 5:
                    // Verifica se o primeiro literal está negado e o segundo também
                    if (tt.compareFormulaChar(formula, 0, '~') && tt.compareFormulaChar(formula, 3, '~')) {
                        tabOk = tt.find2VariableFormula(formula);
                        break;
                    }
                // Qualquer fórmula de tamanho 5 que não tenha as negações
                // nos campos 0 e 4, é uma fórmula com 3 literais
                case 6:
                // A partir do tamanho 6, todas as fórmulas tem 3 variáveis
                case 7:
                case 8:
                    tabOk = tt.find3VariableFormula(formula);
                    break;
                default:
                    // Qualquer tamanho de fórmula inserida maior que 8 não está
                    // correta ou não é suportada pelo programa.
                    tabOk = false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if (!tabOk) {
            System.out.println("Tabela não gerada. Fórmula incorreta.");
        }
    }
}
