export default {
    server: (process.env?.BACKEND_SERVER == null ? "http://localhost:8080" : process.env.BACKEND_SERVER),
}
