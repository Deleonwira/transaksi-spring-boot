-- phpMyAdmin SQL Dump (versi idempotent untuk kelas)

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+07:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
 /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
 /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 /*!40101 SET NAMES utf8mb4 */;

-- =========================================================
-- Database: `penjualan`
-- =========================================================

-- ---------------------------------------------------------
-- 1. TABEL MASTER (TANPA FOREIGN KEY KE TABEL LAIN)
-- ---------------------------------------------------------

-- Tabel JENIS_BARANG
CREATE TABLE IF NOT EXISTS `jenis_barang` (
  `id_jenis_brg` tinyint(4) NOT NULL AUTO_INCREMENT,
  `nama_jenis_brg` varchar(20) NOT NULL,
  PRIMARY KEY (`id_jenis_brg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `jenis_barang` (`id_jenis_brg`, `nama_jenis_brg`) VALUES
(100, 'Snack'),
(101, 'Minuman'),
(102, 'Elektronik'),
(103, 'Alat Pembersih'),
(104, 'Snack'),
(105, 'Minuman'),
(106, 'Elektronik'),
(107, 'Alat Pembersih'),
(108, 'Snack'),
(109, 'Minuman'),
(110, 'Elektronik'),
(111, 'Alat Pembersih');

-- Tabel PEMASOK
CREATE TABLE IF NOT EXISTS `pemasok` (
  `id_pemasok` char(4) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `telepon` varchar(15) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id_pemasok`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `pemasok` (`id_pemasok`, `nama`, `alamat`, `telepon`, `email`) VALUES
('S001', 'Pungkas Mandiri', 'Jl. Merapi No.212', '08122848418', 'pt_puma@puma.com'),
('S002', 'Djati Internusa', 'Jl. Semeru No.44', '081328139050', NULL),
('S003', 'Sabar Subur', 'Jl. Galunggung No.66', '08157984898', NULL),
('S004', 'Kiat Sejahtera', 'Jl. Sutami No.545', '081931771735', 'sejahtera.kiat@yahoo.co.id'),
('S005', 'Bersemi Kembali', 'Jl. Sigura-gura No. 7A', '081328171718', NULL),
('S006', 'Isaku Niki', 'Jl. Kawi No. 27', '08126702381', 'yoiki_isakudab@gmail.com'),
('S007', 'Indo Mandiri', 'Jl. Muria No. 34B', '081914057581', 'mandirindo@gomandiri.org'),
('S008', 'Karya Indah', 'Jl. Merapi No. 35B', '081328084248', NULL);

-- Tabel KARYAWAN
CREATE TABLE IF NOT EXISTS `karyawan` (
  `id_karyawan` char(4) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `jenis_kelamin` char(1) NOT NULL DEFAULT 'L',
  `alamat` varchar(50) NOT NULL,
  `telepon` varchar(15) DEFAULT NULL,
  `tgl_lahir` date NOT NULL,
  `gaji` double NOT NULL,
  PRIMARY KEY (`id_karyawan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `karyawan` (`id_karyawan`, `nama`, `jenis_kelamin`, `alamat`, `telepon`, `tgl_lahir`, `gaji`) VALUES
('K001', 'Tuti', 'P', 'Jl. Kenanga No.8', '08155601135', '1984-02-13', 725069),
('K002', 'Siti', 'P', 'Jl. Mawar No.36B', '085868426728', '1983-08-29', 900269),
('K003', 'Endang', 'P', 'Jl. Anggrek No.1', '087811121097', '1982-11-02', 525000),
('K004', 'Yuli', 'P', 'Jl. Melati No.19', '081327934634', '1980-06-03', 622349),
('K005', 'Yola', 'P', 'Jl. Melati No. 80', '081328305938', '1983-09-11', 425600),
('K006', 'Lisa', 'P', 'Jl. Kamboja No. 11', '081572244100', '1980-05-02', 731000),
('K007', 'Hasan', 'L', 'Jl. Bungaku No. 7', '08123476772', '1978-10-06', 725800),
('K008', 'Herry', 'L', 'Jl. Bungamu No. 8B', '08125434789', '1982-03-26', 715100);

-- Tabel PELANGGAN
CREATE TABLE IF NOT EXISTS `pelanggan` (
  `id_pelanggan` char(4) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `jenis_kelamin` char(1) NOT NULL DEFAULT 'L',
  `alamat` varchar(50) NOT NULL,
  `telepon` varchar(15) DEFAULT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_pelanggan` char(1) NOT NULL DEFAULT 'S',
  PRIMARY KEY (`id_pelanggan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `pelanggan` (`id_pelanggan`, `nama`, `jenis_kelamin`, `alamat`, `telepon`, `tgl_lahir`, `jenis_pelanggan`) VALUES
('P001', 'Andi', 'L', 'Jl. Sukarno Hatta No.80', '08159132629', '1984-07-16', 'S'),
('P002', 'Susanto', 'L', 'Jl. P. Sudirman No.9', '08189596566', '1983-07-11', 'G'),
('P003', 'Susanti', 'L', 'Jl. Hayam Wuruk No.9A', '08171122222', '1982-08-02', 'G'),
('P004', 'Anton', 'L', 'Jl. Kebon Jeruk No.9B', '08171122223', '1980-06-12', 'S'),
('P005', 'Bobby', 'L', 'Jl. Kebon Sirih No. 80', '081310202326', '1983-05-01', 'G'),
('P006', 'Charles', 'L', 'Jl. P. Diponegoro No. 89', '081399919232', '1980-04-12', 'G'),
('P007', 'Merry', 'P', 'Jl. Kaliurang KM. 6 No. 80', '08125656989', '1978-05-16', 'G'),
('P008', 'Meiliana', 'P', 'Jl. Sukarno Hatta No. 78B', '08164918722', '1986-04-04', 'S');

-- ---------------------------------------------------------
-- 2. TABEL YANG PUNYA FOREIGN KEY
-- ---------------------------------------------------------

-- Tabel BARANG (FK ke jenis_barang & pemasok)
CREATE TABLE IF NOT EXISTS `barang` (
  `id_barang` char(4) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `stok` smallint(6) NOT NULL,
  `harga` double NOT NULL,
  `persen_laba` double NOT NULL DEFAULT 5,
  `diskon` double NOT NULL DEFAULT 0,
  `id_jenis_brg` tinyint(4) NOT NULL,
  `id_pemasok` char(4) NOT NULL,
  PRIMARY KEY (`id_barang`),
  KEY `fk_id_jenis_brg` (`id_jenis_brg`),
  KEY `fk_id_pemasok` (`id_pemasok`),
  CONSTRAINT `fk_id_jenis_brg` FOREIGN KEY (`id_jenis_brg`)
      REFERENCES `jenis_barang` (`id_jenis_brg`)
      ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_id_pemasok` FOREIGN KEY (`id_pemasok`)
      REFERENCES `pemasok` (`id_pemasok`)
      ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `barang` (`id_barang`, `nama`, `stok`, `harga`, `persen_laba`, `diskon`, `id_jenis_brg`, `id_pemasok`) VALUES
('B001', 'Chitato', 200, 3500, 5, 10, 101, 'S001'),
('B002', 'Cheetos', 100, 2850, 3, 10, 101, 'S001'),
('B003', 'Sprite', 125, 1650, 7, 8, 102, 'S003'),
('B004', 'Taro', 120, 2300, 6, 10, 101, 'S001'),
('B005', 'Fanta', 80, 1450, 6, 7, 102, 'S003'),
('B006', 'Sapu', 50, 2250, 10, 0, 104, 'S006'),
('B007', 'Kain Pel', 60, 2800, 4, 0, 104, 'S007'),
('B008', 'Coca Cola', 56, 1450, 3, 8, 102, 'S008'),
('B009', 'Seven Up', 65, 1850, 4, 8, 102, 'S008'),
('B010', 'Pepsi', 89, 1750, 6, 8, 102, 'S001'),
('B011', 'Telepon Panasonic', 75, 230000, 8, 0, 103, 'S004'),
('B012', 'Televisi Sharp', 30, 1400000, 7, 0, 103, 'S005'),
('B013', 'Ember', 78, 15000, 5, 0, 104, 'S006');

-- Tabel TRANSAKSI (FK ke pelanggan & karyawan)
CREATE TABLE IF NOT EXISTS `transaksi` (
  `kode_transaksi` char(4) NOT NULL,
  `tgl_transaksi` datetime NOT NULL,
  `id_pelanggan` char(4) NOT NULL,
  `id_karyawan` char(4) NOT NULL,
  PRIMARY KEY (`kode_transaksi`),
  KEY `fk_id_karyawan` (`id_karyawan`),
  KEY `fk_id_pelanggan` (`id_pelanggan`),
  CONSTRAINT `fk_id_karyawan` FOREIGN KEY (`id_karyawan`)
      REFERENCES `karyawan` (`id_karyawan`)
      ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_id_pelanggan` FOREIGN KEY (`id_pelanggan`)
      REFERENCES `pelanggan` (`id_pelanggan`)
      ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `transaksi` (`kode_transaksi`, `tgl_transaksi`, `id_pelanggan`, `id_karyawan`) VALUES
('J001', '2006-05-05 11:30:55', 'P001', 'K001'),
('J002', '2006-05-08 10:10:05', 'P002', 'K002'),
('J003', '2006-05-12 10:45:12', 'P001', 'K003'),
('J004', '2006-05-15 17:57:09', 'P003', 'K004'),
('J005', '2006-05-16 18:01:31', 'P004', 'K005'),
('J006', '2006-05-18 16:42:41', 'P002', 'K001'),
('J007', '2006-05-20 11:31:13', 'P005', 'K002'),
('J008', '2006-05-22 10:30:05', 'P006', 'K003'),
('J009', '2006-05-24 20:19:19', 'P006', 'K004'),
('J010', '2006-05-29 20:39:46', 'P007', 'K005'),
('J011', '2006-06-02 19:16:01', 'P006', 'K001'),
('J012', '2006-06-05 19:00:12', 'P008', 'K002'),
('J013', '2006-06-10 14:38:11', 'P007', 'K003'),
('J014', '2006-06-12 13:10:52', 'P006', 'K004');

-- Tabel DETAIL_TRANSAKSI (FK ke barang & transaksi)
CREATE TABLE IF NOT EXISTS `detail_transaksi` (
  `kode_transaksi` char(4) NOT NULL,
  `id_barang` char(4) NOT NULL,
  `jumlah` smallint(6) NOT NULL,
  PRIMARY KEY (`kode_transaksi`,`id_barang`),
  KEY `fk_id_barang` (`id_barang`),
  CONSTRAINT `fk_id_barang` FOREIGN KEY (`id_barang`)
      REFERENCES `barang` (`id_barang`)
      ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_kode_transaksi` FOREIGN KEY (`kode_transaksi`)
      REFERENCES `transaksi` (`kode_transaksi`)
      ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO `detail_transaksi` (`kode_transaksi`, `id_barang`, `jumlah`) VALUES
('J001', 'B001', 5),
('J001', 'B002', 2),
('J001', 'B003', 3),
('J002', 'B001', 2),
('J003', 'B001', 3),
('J003', 'B005', 2),
('J004', 'B004', 5),
('J004', 'B007', 4),
('J004', 'B008', 2),
('J005', 'B011', 1),
('J005', 'B013', 4),
('J006', 'B006', 3),
('J007', 'B001', 3),
('J007', 'B008', 2),
('J008', 'B001', 2),
('J008', 'B004', 2),
('J008', 'B008', 5),
('J009', 'B001', 3),
('J009', 'B006', 4),
('J010', 'B013', 2),
('J011', 'B001', 2),
('J011', 'B002', 4),
('J012', 'B002', 4),
('J012', 'B009', 2),
('J013', 'B001', 2),
('J013', 'B002', 4),
('J013', 'B008', 4),
('J014', 'B002', 2),
('J014', 'B006', 3);

-- ---------------------------------------------------------
-- 3. TABEL PENGGUNA (UNTUK AUTENTIKASI)
-- ---------------------------------------------------------

-- Tabel PENGGUNA
-- Role: ADMIN (administrator), KARYAWAN (kasir/staff), PELANGGAN (customer), PEMASOK (supplier)
-- Hanya satu dari id_karyawan, id_pelanggan, atau id_pemasok yang diisi sesuai role
CREATE TABLE IF NOT EXISTS `pengguna` (
  `id_pengguna` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'KARYAWAN',
  `id_karyawan` char(4) DEFAULT NULL,
  `id_pelanggan` char(4) DEFAULT NULL,
  `id_pemasok` char(4) DEFAULT NULL,
  `aktif` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_pengguna`),
  UNIQUE KEY `uk_pengguna_username` (`username`),
  KEY `fk_pengguna_karyawan` (`id_karyawan`),
  KEY `fk_pengguna_pelanggan` (`id_pelanggan`),
  KEY `fk_pengguna_pemasok` (`id_pemasok`),
  CONSTRAINT `fk_pengguna_karyawan` FOREIGN KEY (`id_karyawan`)
      REFERENCES `karyawan` (`id_karyawan`)
      ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_pengguna_pelanggan` FOREIGN KEY (`id_pelanggan`)
      REFERENCES `pelanggan` (`id_pelanggan`)
      ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_pengguna_pemasok` FOREIGN KEY (`id_pemasok`)
      REFERENCES `pemasok` (`id_pemasok`)
      ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Password yang di-encode dengan BCrypt (strength 12):
-- admin123 => $2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/X4RUKRNBXIqMjOVfO
-- user123  => $2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe

INSERT IGNORE INTO `pengguna` (`id_pengguna`, `username`, `password`, `role`, `id_karyawan`, `id_pelanggan`, `id_pemasok`, `aktif`) VALUES
-- Admin (Karyawan K001 - Tuti)
(1, 'admin', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/X4RUKRNBXIqMjOVfO', 'ADMIN', 'K001', NULL, NULL, 1),
-- Karyawan
(2, 'siti', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'KARYAWAN', 'K002', NULL, NULL, 1),
(3, 'endang', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'KARYAWAN', 'K003', NULL, NULL, 1),
(4, 'hasan', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'KARYAWAN', 'K007', NULL, NULL, 1),
-- Pelanggan
(5, 'andi', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'PELANGGAN', NULL, 'P001', NULL, 1),
(6, 'susanto', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'PELANGGAN', NULL, 'P002', NULL, 1),
(7, 'merry', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'PELANGGAN', NULL, 'P007', NULL, 1),
-- Pemasok
(8, 'pungkas', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'PEMASOK', NULL, NULL, 'S001', 1),
(9, 'djati', '$2a$12$WQYPgi1HqggC8KqVxmqVIOSpKlHfBGqRlGIe8xF2EM0DLLudPpTKe', 'PEMASOK', NULL, NULL, 'S002', 1);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
 /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
 /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
-- End of dump