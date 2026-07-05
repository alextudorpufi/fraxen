document.addEventListener('DOMContentLoaded', function () {
    const grid = document.querySelector('.executives-grid');

    fetch('/api/executives')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Request failed with status ${response.status}`);
            }
            return response.json();
        })
        .then(executives => {
            grid.innerHTML = ''; // Clear loading message

            if (!executives.length) {
                grid.innerHTML = `
                    <div class="executives-empty">
                        <p>No executives are available yet.</p>
                    </div>
                `;
                return;
            }

            executives.forEach(exec => {
                const genderLower = (exec.gender || '').toLowerCase();
                const genderDisplay = genderLower.charAt(0).toUpperCase() + genderLower.slice(1);

                const card = document.createElement('div');
                card.className = 'executive-card';
                card.setAttribute('data-gender', genderLower);

                card.innerHTML = `
                    <div class="exec-avatar">
                        <img src="/images/user.svg" alt="User" class="icon">
                    </div>
                    <div class="exec-details">
                        <h2>${exec.title || ''}</h2><br>
                        <p><strong>Gender:</strong> ${genderDisplay}</p>
                        <p><strong>Experience:</strong> ${exec.experience || ''}</p>
                        <p><strong>Sector Focus:</strong> ${exec.sectorFocus || ''}</p>
                        <p><strong>Location:</strong> ${exec.location || ''}</p>
                        ${exec.highlights?.map(h => {
                    const formattedDetails = (h.details || '')
                        .replace(/\\n/g, '\n')  // convert literal "\n" to real newline
                        .split('\n')             // now split on real newline
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
            grid.innerHTML = `
                <div class="executives-error">
                    <p>Executives could not be loaded right now.</p>
                </div>
            `;
        });
});
