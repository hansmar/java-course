public static void main(String[] args) {
    BoardDisplay board = new BoardDisplay(4, 7);

    // 1. Set Content
    board.set(1, 1, "BB");
    board.set(2, 3, "AA");
    board.set(3, 6, "gg");
    board.set(4, 7, "CC");

    // 2. Set East Walls (The vertical bars inside rows)
    board.setEastWall(2, 3); // The bar right of AA

    // 3. Set South Walls (The horizontal dashes between rows)
    board.setSouthWall(1, 4); // First inner wall on the row below BB
    board.setSouthWall(1, 5); // Second inner wall on the row below BB
    board.setSouthWall(2, 3); // The wall directly under AA

    board.show();
}
