INSERT INTO categories (category_id, name) VALUES
    (1, 'Technology'),
    (2, 'Science'),
    (3, 'Health')
ON CONFLICT (category_id) DO NOTHING;

INSERT INTO websites (website_id, name, url) VALUES
    (1, 'TechCrunch', 'https://techcrunch.com'),
    (2, 'ScienceDaily', 'https://www.sciencedaily.com'),
    (3, 'Healthline', 'https://www.healthline.com')
ON CONFLICT (website_id) DO NOTHING;
