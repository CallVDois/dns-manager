# callv2-dns-manager

## 🌐 Environment Variables

Below is a list of environment variables required to configure the DNS updater container for Cloudflare:

| Variable Name              | Default | Description                                                                 | Example                          |
|---------------------------|----------|-----------------------------------------------------------------------------|----------------------------------|
| `CLOUDFLARE_API_TOKEN`    | empty   | Cloudflare API token with permission to manage DNS records.                | `abc1234567890xyz`               |
| `CLOUDFLARE_ZONE_ID`      | empty   | The Zone ID of the domain in Cloudflare.                                   | `987zyx654321cba`                |
| `DNS_PROVIDER`            | cloudflare   | DNS provider to use. Currently only `cloudflare` is supported.             | `cloudflare`                     |
| `DNS_UPDATE_DELAY_MS`     | 600000    | Delay between DNS update checks, in milliseconds.                          | `600000` (10 minutes)            |
| `A_DNS_LIST`              | empty    | Comma-separated list of `A` (IPv4) DNS records to update.                  | `example.com,www.example.com`    |
| `AAAA_DNS_LIST`           | empty    | Comma-separated list of `AAAA` (IPv6) DNS records to update.               | `ipv6.example.com`               |
| `WEBHOOK_URL`           | empty    | Discord webhook URL to receive notifications about DNS updates.               | `https://discord.com/api/webhooks/...`               |

> ⚠️ **Never include secrets like `CLOUDFLARE_API_TOKEN` directly in the Dockerfile.** Use a `.env` file or pass them through your container orchestrator's secret management system (e.g., Docker Compose, Kubernetes, etc.).

---

### 📄 .env.example

You can copy and rename this example file as `.env` and fill in your actual credentials:

```dotenv
CLOUDFLARE_API_TOKEN=your_cloudflare_api_token
CLOUDFLARE_ZONE_ID=your_cloudflare_zone_id

DNS_PROVIDER=cloudflare
DNS_UPDATE_DELAY_MS=600000

A_DNS_LIST=example.com,www.example.com
AAAA_DNS_LIST=ipv6.example.com

WEBHOOK_URL=your_discord_webhook_url

