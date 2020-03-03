package com.sergeyglushkov.calculator;

import java.util.Stack;

class Calculator {

    //Метод вычисления выражения
    double decide(String expression) {
        String prepared = preparingExpression(expression);
        String rpn = expressionToRPN(prepared);
        return calculateRPN(rpn);
    }

    //Преобразуем строку в Обратную Польскую нотацию
    private static String expressionToRPN(String expression) {

        String current = "";
        Stack<Character> stack = new Stack<>();

        int priority;
        for (int i = 0; i < expression.length(); i++) {
            priority = getPriority(expression.charAt(i));

            if (priority == 0) {
                current += expression.charAt(i);
            }
            if (priority == 1) {
                stack.push(expression.charAt(i));
            }
            if (priority > 1) {
                current += ' ';

                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) {
                        current += stack.pop();
                    } else break;
                }
                stack.push(expression.charAt(i));
            }
            if (priority == -1) {
                current += ' ';
                while (getPriority(stack.peek()) != 1) {
                    current += stack.pop();
                }
                stack.pop();
            }
        }
        while (!stack.empty()) {
            current += stack.pop();
        }

        return current;
    }

    //Вычисляем ОПН
    private static double calculateRPN(String rpn) {
        String operand = new String();
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                }
                stack.push(Double.parseDouble(operand));
                operand = new String();
            }

            if (getPriority(rpn.charAt(i)) > 1) {
                double a = stack.pop();
                double b = stack.pop();

                if (rpn.charAt(i) == '+') {
                    stack.push(b + a);
                }
                if (rpn.charAt(i) == '-') {
                    stack.push(b - a);
                }
                if (rpn.charAt(i) == '*') {
                    stack.push(b * a);
                }
                if (rpn.charAt(i) == '/') {
                    stack.push(b / a);
                }
            }

        }
        return stack.pop();
    }

    //Назначаем приоритет для символов
    private static byte getPriority(char token) {
        if (token == '*' || token == '/') {
            return 3;
        } else if (token == '+' || token == '-') {
            return 2;
        } else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return -1;
        } else return 0;
    }

    //Добавляем возможность использовать унарный минус
    private String preparingExpression(String expression) {
        String preparedExpression = new String();
        for (int token = 0; token < expression.length(); token++) {
            char symbol = expression.charAt(token);
            if (symbol == '-') {
                if (token == 0) {
                    preparedExpression += 0;
                } else if (expression.charAt(token - 1) == ('(')) {
                    preparedExpression += '0';
                }
            }
            preparedExpression += symbol;
        }
        return preparedExpression;
    }
}
