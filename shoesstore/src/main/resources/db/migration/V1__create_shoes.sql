CREATE TABLE IF NOT EXISTS shoes (
                                     id UUID PRIMARY KEY,
                                     sku VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    brand VARCHAR(80) NOT NULL,
    description VARCHAR(500),
    price_amount NUMERIC(12,2) NOT NULL,
    price_currency CHAR(3) NOT NULL,
    stock INT NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_shoes_active ON shoes(active);
CREATE INDEX IF NOT EXISTS idx_shoes_brand ON shoes(brand);
