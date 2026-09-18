-- Bootstraps a single Admin account so the system is reachable on a fresh database.
-- Login: admin@fpt.edu.vn / Admin@123  -- CHANGE THIS PASSWORD IMMEDIATELY in any non-local environment.
INSERT INTO users (id, email, full_name, password_hash, role, status, created_at, updated_at)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'admin@fpt.edu.vn',
    'System Admin',
    '$2b$10$JhjE1P6m28aTN7z8KDcdVuPbff7YZl3UjwuF5PVWKa2ysTzJqrQy.',
    'ADMIN',
    'ACTIVE',
    now(),
    now()
);
