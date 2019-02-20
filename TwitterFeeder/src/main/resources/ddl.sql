CREATE TABLE IF NOT EXISTS tweets (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  link TEXT,
  track TEXT,
  content TEXT,
  title TEXT,
  description TEXT,
  screenshotURL TEXT,
  timestamp DATE DEFAULT (datetime('now', 'localtime'))
)