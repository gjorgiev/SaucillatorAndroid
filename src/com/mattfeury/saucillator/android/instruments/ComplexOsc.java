package com.mattfeury.saucillator.android.instruments;

import java.util.LinkedList;

import com.mattfeury.saucillator.android.sound.Limiter;

/**
 * A complex oscillator.
 * An oscillator that is made up of BasicOscs and sums them together.
 */
public class ComplexOsc extends Oscillator {

  protected LinkedList<Oscillator> components;
  public static final float MAX_AMPLITUDE = 1.0f;

  public ComplexOsc() {
    this(1.0f);
  }
  public ComplexOsc(float amp) {
    amplitude = amp;
    components = new LinkedList<Oscillator>();
  }

  public void fill(Oscillator... oscs) {
    for(Oscillator osc : oscs) {
      osc.setPlaying(true); //we manage playback here, so all the children should be available for rendering
      components.add(osc);
      osc.chuck(this);
    }
  }
  public LinkedList<Oscillator> getComponents() {
    return components;
  }
  public Oscillator getComponent(int index) {
    return components.get(index);
  }

  public void setFreq(float freq) {
    for(Oscillator osc : components)
      osc.setFreq(freq * this.harmonic);
  }

  public void setModRate(int rate) {
    for(Oscillator osc : components)
      osc.setModRate(rate);
  }
  public void setModDepth(int depth) {
    for(Oscillator osc : components)
      osc.setModDepth(depth);
  }

  //TODO FIXME this assumes they all have the same LFO settings. is this right?
  public int getModRate() {
    for(Oscillator osc : components)
      return osc.getModRate();

    return 0;
  }
  public int getModDepth() {
    for(Oscillator osc : components)
      return osc.getModDepth();

    return 0;
  }

  public void setLag(float rate) {
    for(Oscillator osc : components)
      osc.setLag(rate);
  }
  public void setAmplitude(float amp) {
    for(Oscillator osc : components)
      osc.setAmplitude(amp);
  }
  public void factorAmplitude(float factor) {
    for(Oscillator osc : components)
      osc.factorAmplitude(factor);
  }
  @Override
  public void startAttack() {
    super.startAttack();

    for(Oscillator osc : components)
      osc.startAttack();    
  }
  @Override
  public void startRelease() {
    super.startRelease();

    for(Oscillator osc : components)
      osc.startRelease();    
  }

  public synchronized boolean render(final float[] buffer) { // assume t is in 0.0 to 1.0
		if(! isPlaying()) {
			return true;
		}

    Limiter.limit(buffer);
    // TODO use amplitude ('volume') here, not the children
    boolean isClean = ! renderKids(buffer);

    rendered();

    return isClean;
	}
}
