package module3;

public class AsciiCharSequence implements java.lang.CharSequence {
    protected byte[] bytes;

    public AsciiCharSequence(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    @Override
    public int length() {
        return bytes.length;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= length())
            return 0;
        return (char) bytes[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || start > end)
            return null;
        byte[] newBytes = new byte[end - start];
        int k = 0;
        for (int i = start; i < end; i++) {
            newBytes[k++] = bytes[i];
        }
        return new AsciiCharSequence(newBytes);
    }

    @Override
    public String toString() {
        return new String(bytes);
    }
}
