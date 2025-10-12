document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/executives')
        .then(response => response.json())
        .then(executives => {
            const grid = document.querySelector('.executives-grid');
            grid.innerHTML = ''; // Clear existing cards

            executives.forEach(exec => {
                const genderLower = (exec.gender || '').toLowerCase(); // sanitize to lowercase
                const genderDisplay = genderLower.charAt(0).toUpperCase() + genderLower.slice(1); // Capitalize first letter

                const card = document.createElement('div');
                card.className = 'executive-card';
                card.setAttribute('data-gender', genderLower); // always lowercase

                card.innerHTML = `
                    <div class="exec-avatar">
                        <img src="images/user.svg" alt="User" class="icon">
                    </div>
                    <div class="exec-details">
                        <h2>${exec.title || ''}</h2>
                        <br>
                        <p><strong>Gender:</strong> ${genderDisplay}</p>
                        <p><strong>Experience:</strong> ${exec.experience || ''}</p>
                        <p><strong>Sector Focus:</strong> ${exec.sectorFocus || ''}</p>
                        <p><strong>Location:</strong> ${exec.location || ''}</p>
                        ${exec.highlights?.map(h => {
                    const formattedDetails = (h.details || '')
                        .replace(/\\n/g, '\n')
                        .split('\n')
                        .map(line => `→ ${line}`)
                        .join('<br>');
                    return `
                        <div class="exec-highlight">
                            <p><strong>${h.positionTitle || ''}</strong> – ${h.companyDescription || ''}<br>
                            ${formattedDetails}</p><br>
                        </div>
                    `;
                                }).join('')}
                        ${exec.strengths?.length ? `
                            <div class="exec-highlight">
                                <p><strong>Core Strengths:</strong><br>
                                ${exec.strengths.map(s => `→ ${s.strengthDescription}<br>`).join('')}</p><br>
                            </div>
                        ` : ''}
                    </div>
                `;
                grid.appendChild(card);
            });
        })
        .catch(err => {
            console.error('Failed to fetch executives:', err);
        });
});
