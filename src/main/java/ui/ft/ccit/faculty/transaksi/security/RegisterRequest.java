package ui.ft.ccit.faculty.transaksi.security;

public class RegisterRequest {
    private String username;
    private String password;
    private String idPelanggan;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String idPelanggan) {
        this.username = username;
        this.password = password;
        this.idPelanggan = idPelanggan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }
}
