const canvas = document.getElementById('snowCanvas');
const ctx = canvas.getContext('2d');

let width, height, snowflakes = [];

// Adjust canvas size to window
function resize() {
    width = canvas.width = window.innerWidth;
    height = canvas.height = window.innerHeight;
}

window.addEventListener('resize', resize);
resize();

// Snowflake properties
class Snowflake {
    constructor() {
        this.init();
    }

    init() {
        this.x = Math.random() * width;        // Random horizontal position
        this.y = Math.random() * height * -1;  // Start above the screen
        this.size = Math.random() * 3 + 2;     // Radius between 2 and 5
        this.speed = Math.random() * 1 + 0.5;  // Falling speed
        this.velX = Math.random() * 0.5 - 0.25; // Slight horizontal drift
        this.opacity = Math.random() * 0.5 + 0.3;
    }

    update() {
        this.y += this.speed;
        this.x += this.velX;

        // Reset snowflake if it goes off screen
        if (this.y > height) {
            this.init();
        }
    }

    draw() {
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
        ctx.fillStyle = `rgba(220, 220, 220, ${this.opacity})`;
        ctx.fill();
    }
}

// Create 150 snowflakes
function initSnow() {
    for (let i = 0; i < 150; i++) {
        snowflakes.push(new Snowflake());
    }
}

// The Animation Loop
function animate() {
    ctx.clearRect(0, 0, width, height); // Clear the canvas each frame

    snowflakes.forEach(flake => {
        flake.update();
        flake.draw();
    });

    requestAnimationFrame(animate);
}

initSnow();
animate();