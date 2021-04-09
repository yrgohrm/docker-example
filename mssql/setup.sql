CREATE DATABASE [TheGame];
GO

USE [TheGame];
GO

CREATE TABLE highscore 
(
    highscore_id INT IDENTITY(1,1), 
    player CHAR(3), 
    score INT CHECK (score > 0)
);
CREATE INDEX score_idx ON highscore(score, player, highscore_id);
GO

INSERT INTO highscore 
VALUES 
    ('ABC', 123),
    ('XYZ', 321);
GO
