package testHttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class GZIPResponseStream extends ServletOutputStream {
    protected ByteArrayOutputStream baos = null;
    protected GZIPOutputStream gzipstream = null;
    protected boolean closed = false;
    protected HttpServletResponse response = null;
    protected ServletOutputStream output = null;

    public GZIPResponseStream(HttpServletResponse httpservletresponse) throws IOException {
        this.closed = false;
        this.response = httpservletresponse;
        this.output = httpservletresponse.getOutputStream();
        this.baos = new ByteArrayOutputStream();
        this.gzipstream = new GZIPOutputStream(this.baos);
    }

    public void close() throws IOException {
        if(this.closed) {
            throw new IOException("This output stream has already been closed");
        } else {
            this.gzipstream.finish();
            byte[] abyte = this.baos.toByteArray();
            this.response.setContentLength(abyte.length);
            this.response.addHeader("Content-Encoding", "gzip");
            this.response.setStatus(200);
            this.output.write(abyte);
            this.output.flush();
            this.output.close();
            this.closed = true;
        }
    }

    public void flush() throws IOException {
        if(!this.closed) {
            this.gzipstream.flush();
        }
    }

    public void write(int i) throws IOException {
        if(this.closed) {
            throw new IOException("Cannot write to a closed output stream");
        } else {
            this.gzipstream.write((byte)i);
        }
    }

    public void write(byte[] abyte) throws IOException {
        this.write(abyte, 0, abyte.length);
    }

    public void write(byte[] abyte, int i, int j) throws IOException {
        if(this.closed) {
            throw new IOException("Cannot write to a closed output stream");
        } else {
            this.gzipstream.write(abyte, i, j);
        }
    }

    public boolean closed() {
        return this.closed;
    }
}