CREATE TABLE accounts (
  id BIGSERIAL PRIMARY KEY,
  owner_subject VARCHAR(200) NOT NULL,
  account_number VARCHAR(32) NOT NULL UNIQUE,
  currency CHAR(3) NOT NULL,
  balance NUMERIC(19,2) NOT NULL DEFAULT 0 CHECK (balance >= 0),
  status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE','FROZEN','CLOSED')),
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX idx_accounts_owner ON accounts(owner_subject);
CREATE TABLE transfers (
  id BIGSERIAL PRIMARY KEY,
  reference VARCHAR(36) NOT NULL UNIQUE,
  from_account_id BIGINT NOT NULL REFERENCES accounts(id),
  to_account_id BIGINT NOT NULL REFERENCES accounts(id),
  amount NUMERIC(19,2) NOT NULL CHECK (amount > 0),
  currency CHAR(3) NOT NULL,
  status VARCHAR(20) NOT NULL CHECK (status IN ('COMPLETED','REJECTED')),
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_transfer_different_accounts CHECK (from_account_id <> to_account_id)
);
CREATE INDEX idx_transfers_from ON transfers(from_account_id, created_at DESC);
CREATE INDEX idx_transfers_to ON transfers(to_account_id, created_at DESC);
