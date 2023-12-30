-- Create the quotes table
CREATE TABLE IF NOT EXISTS quotes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quote VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL
);